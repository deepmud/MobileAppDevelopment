//package uk.ac.tees.mad.E4621366.mobileappdevelopment.screen
//
//import android.Manifest
//import android.annotation.SuppressLint
//import android.location.Location
//import android.widget.Toast
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.ImageBitmap
//import androidx.compose.ui.graphics.asImageBitmap
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import com.google.android.gms.location.LocationServices
//import kotlinx.coroutines.delay
//import uk.ac.tees.mad.E4621366.mobileappdevelopment.MyApp
//import uk.ac.tees.mad.E4621366.mobileappdevelopment.component.ProfileBanner
//import uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.common.getAddressFromLocation
//import uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.layout.DashboardFooter
//import uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.layout.DashboardHeader
//import uk.ac.tees.mad.E4621366.mobileappdevelopment.util.rememberGoogleSignIn
//import uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel.AuthViewModel
//import uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel.IndicatorViewModel
//import uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel.PhotoViewModel
//import uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel.PhotoViewModelFactory
//
//@Composable
//fun DashboardScreen3(navController: NavController, authVM: AuthViewModel = viewModel(), indicatorVM: IndicatorViewModel = viewModel()) {
//
//    val context = LocalContext.current
//
//   val googleSignIn = rememberGoogleSignIn(authVM, navController, errorState = remember { mutableStateOf(null) })
//    val fusedClient = LocationServices.getFusedLocationProviderClient(context)
//    var location by remember { mutableStateOf<Location?>(null) }
//   // var imageUri by remember { mutableStateOf<Uri?>(null) }
//    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
//    val app = LocalContext.current.applicationContext as MyApp
//    val photoDao = app.database.photoDao()
//    val vm = null
//    val data by indicatorVM.state.collectAsState()
//    val loading by indicatorVM.loading.collectAsState()
//    val error by indicatorVM.error.collectAsState()
//
//
//    val photoVM: PhotoViewModel = viewModel(
//        factory = PhotoViewModelFactory(photoDao)
//    )
//
//    val photos by photoVM.photos.collectAsState()
//
//
//    val locationText1 by remember(location) {
//        mutableStateOf(
//            location?.let {
//                val lat = String.format("%.5f", it.latitude)
//                val lng = String.format("%.5f", it.longitude)
//                "$lat~$lng"
//            } ?: "Fetching location…"
//        )
//    }
//
//    val locationText by remember(location) {
//        mutableStateOf(
//            location?.let {
//                getAddressFromLocation(context, it.latitude, it.longitude)
//            } ?: "Unknown location"
//        )
//    }
//
//
//    val cameraLauncher =
//        rememberLauncherForActivityResult(
//            ActivityResultContracts.TakePicturePreview()
//        ) { bitmap ->
//            // You can convert bitmap to ImageBitmap
//            bitmap?.let {
//                imageBitmap = it.asImageBitmap()
//                photoVM.savePhoto(context, it) // save to disk + Room
//            }
//        }
//
//
//    val cameraPermissionLauncher =
//        rememberLauncherForActivityResult(
//            ActivityResultContracts.RequestPermission()
//        ) { granted ->
//            if (granted) {
//                cameraLauncher.launch(null) //  launch ONLY if granted
//            } else {
//                Toast
//                    .makeText(context, "Camera permission denied", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        }
//
//    val permissionLauncher =
//        rememberLauncherForActivityResult(
//            ActivityResultContracts.RequestPermission()
//        )
//        { granted ->
//            if (granted) {
//                @SuppressLint("MissingPermission")
//                fusedClient.lastLocation.addOnSuccessListener {
//                    location = it // the same as (loc -> location = loc)
//                }
//            }
//        }
//
//
//    Column(
//        modifier = Modifier.fillMaxSize()
//    ) {
//
//    LaunchedEffect(Unit) {
//        delay(500)
//
//        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//        indicatorVM.loadIndicators(
//            indicator = "HEPATITIS_B",
//            country = "NG",
//            year = 2023
//        )
//    }
//
//    when {
//        loading -> Text("Loading…")
//        error != null -> Text("Error: $error")
//        else -> LazyColumn {
//            items(data) { item ->
//                Text("${item.name}: ${item.value}")
//            }
//        }
//    }
//
//
//    DashboardHeader(
//        appName = "EmtechEHR",
//        userName = authVM?.getCurrentUserEmail()?.substringBefore("@")
//            ?.take(8),
//       profileImage = photos?.firstOrNull()?.imagePath,
//        cameraPermission = {
//            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
//        },
//        onLogout = {
//                authVM.logout()
//                // Sign out from Google client to force account picker
//                googleSignIn.client.signOut()
//                navController.navigate("login") {
//                    popUpTo("dashboard") { inclusive = true }
//                }
//
//        }
//    )
//
//        Column(
//            modifier = Modifier
//                .weight(1f)
//                .verticalScroll(rememberScrollState())
//        ) {
//
//            ProfileBanner(photos?.firstOrNull()?.imagePath)
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            this@LazyColumn.dashboardMenuGrid(navController)
//
//            Spacer(modifier = Modifier.height(20.dp))
//        }
//
//
//        DashboardFooter(
//            location = locationText
//
//        )
//
//
//    }
//
//
//
//}
//
//
//
//
//
//
////    LazyColumn {
////        items(photos) { photo ->
////            Image(
////                bitmap = BitmapFactory
////                    .decodeFile(photo.imagePath)
////                    .asImageBitmap(),
////                contentDescription = null,
////                modifier = Modifier
////                    .fillMaxWidth()
////                    .height(200.dp)
////                    .padding(8.dp)
////            )
////        }
////    }
//
//
//
