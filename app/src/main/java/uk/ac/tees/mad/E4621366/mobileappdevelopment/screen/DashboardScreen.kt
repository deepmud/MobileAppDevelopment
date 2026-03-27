package uk.ac.tees.mad.E4621366.mobileappdevelopment.screen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.delay
import uk.ac.tees.mad.E4621366.mobileappdevelopment.MyApp
import uk.ac.tees.mad.E4621366.mobileappdevelopment.component.dashboardMenuGrid

import uk.ac.tees.mad.E4621366.mobileappdevelopment.component.ProfileBanner
import uk.ac.tees.mad.E4621366.mobileappdevelopment.common.getAddressFromLocation
import uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.layout.DashboardFooter
import uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.layout.DashboardHeader
import uk.ac.tees.mad.E4621366.mobileappdevelopment.util.rememberGoogleSignIn
import uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel.AuthViewModel
import uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel.IndicatorViewModel
import uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel.LocationViewModel
import uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel.PhotoViewModel
import uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel.PhotoViewModelFactory
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.gms.location.Priority

@Composable
fun DashboardScreen(navController: NavController, authVM: AuthViewModel = viewModel()) {

    val context = LocalContext.current

    val googleSignIn =
        rememberGoogleSignIn(authVM, navController, errorState = remember { mutableStateOf(null) })
    val fusedClient = LocationServices.getFusedLocationProviderClient(context)

    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    val app = LocalContext.current.applicationContext as MyApp
    val photoDao = app.database.photoDao()
    val vm = null

    val locationVM: LocationViewModel = viewModel()
    val location by locationVM.location.collectAsState()
    val locationStatus by locationVM.status.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current


    val photoVM: PhotoViewModel = viewModel(
        factory = PhotoViewModelFactory(photoDao)
    )

    val photos by photoVM.photos.collectAsState()


    val locationText1 by remember(location) {
        androidx.compose.runtime.derivedStateOf {
            location?.let {
                val lat = String.format("%.5f", it.latitude)
                val lng = String.format("%.5f", it.longitude)
                "$lat~$lng"
            } ?: "Fetching location…"
        }
    }

    val locationText by remember(location, locationStatus) {
        derivedStateOf {
            when (locationStatus) {
                "OFF" -> "Location is off"
                "DENIED" -> "Location permission denied"
                "FETCHING" -> "Fetching location…"
                "OK" -> location?.let { getAddressFromLocation(context, it.latitude, it.longitude) }
                    ?: "Unknown"

                else -> "Location not available"
            }
        }
    }


    val cameraLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.TakePicturePreview()
        ) { bitmap ->
            // You can convert bitmap to ImageBitmap
            bitmap?.let {
                imageBitmap = it.asImageBitmap()
                photoVM.savePhoto(context, it) // save to disk + Room
            }
        }


    val cameraPermissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (granted) {
                cameraLauncher.launch(null) //  launch ONLY if granted
            } else {
                Toast
                    .makeText(context, "Camera permission denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->

            if (!granted) return@rememberLauncherForActivityResult

            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            val gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)


            if (!gpsEnabled) {
                Toast.makeText(context, "Turn on location service", Toast.LENGTH_LONG).show()
                context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                return@rememberLauncherForActivityResult
            }

//            @SuppressLint("MissingPermission")
//            fusedClient.getCurrentLocation(
//                com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
//                null
//            ).addOnSuccessListener { loc ->
//
//                if (loc != null) {
//                    locationVM.setLocation(loc)
//                } else {
//                    Toast.makeText(context, "Fetching location… move outdoors", Toast.LENGTH_LONG)
//                        .show()
//                }
//            }

            @SuppressLint("MissingPermission")
            fusedClient.lastLocation.addOnSuccessListener { loc ->
                if (loc != null) {
                    locationVM.setLocation(loc)
                } else {
                    // fallback only if null
                    fusedClient.getCurrentLocation(
                        Priority.PRIORITY_HIGH_ACCURACY,
                        null
                    ).addOnSuccessListener { fresh ->
                        fresh?.let { locationVM.setLocation(it) }
                    }
                }
            }
        }



    DisposableEffect(lifecycleOwner) {
        val obs = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                locationVM.refresh(context)
            }
        }
        lifecycleOwner.lifecycle.addObserver(obs)
        onDispose { lifecycleOwner.lifecycle.removeObserver(obs) }
    }

    // First load
    LaunchedEffect(Unit) {
        delay(500)

        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)

    }
    LaunchedEffect(Unit) {
        delay(500)

        locationVM.refresh(context)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Scaffold(
            contentWindowInsets = WindowInsets(0),
            containerColor = Color(0xFFE8EAE5),
            bottomBar = {
                DashboardFooter(locationText)
            }
        ) { padding ->

            LazyColumn(
                modifier = Modifier.padding(padding).background(color = Color( 0xffe8eae5)),
            ) {

                stickyHeader {
                    DashboardHeader(
                        appName = "EmtechEHR",

                        onLogout = {
                            authVM.logout()
                            googleSignIn.client.signOut()
                            navController.navigate("login") {
                                popUpTo("dashboard") { inclusive = true }
                            }
                        }
                    )
                }

                item {
                    ProfileBanner(
                        photos.firstOrNull()?.imagePath,
                        userName = authVM.getCurrentUserEmail()
                    )
                }



                item {
                    this@LazyColumn.dashboardMenuGrid(
                        navController,
                        cameraPermissionLauncher = cameraPermissionLauncher
                    )
                }

                item { Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(Color(0xFFE8EAE5))) }


            }


        }


    }

}



