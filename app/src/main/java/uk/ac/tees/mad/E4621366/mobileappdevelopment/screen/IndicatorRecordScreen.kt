package uk.ac.tees.mad.E4621366.mobileappdevelopment.screen

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.android.gms.location.LocationServices
import uk.ac.tees.mad.E4621366.mobileappdevelopment.MyApp
import uk.ac.tees.mad.E4621366.mobileappdevelopment.common.getAddressFromLocation
import uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.layout.DashboardFooter
import uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.layout.DashboardHeader
import uk.ac.tees.mad.E4621366.mobileappdevelopment.util.rememberGoogleSignIn
import uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel.AuthViewModel
import uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel.IndicatorViewModel
import uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel.PhotoViewModel
import uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel.PhotoViewModelFactory
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import uk.ac.tees.mad.E4621366.mobileappdevelopment.component.IndicatorHeader
import uk.ac.tees.mad.E4621366.mobileappdevelopment.component.IndicatorTableRow

@Composable
fun IndicatorRecordScreen(navController: NavController, authVM: AuthViewModel, indicatorVM: IndicatorViewModel) {

    val context = LocalContext.current

   val googleSignIn = rememberGoogleSignIn(authVM, navController, errorState = remember { mutableStateOf(null) })
    val fusedClient = LocationServices.getFusedLocationProviderClient(context)
    var location by remember { mutableStateOf<Location?>(null) }
    val app = LocalContext.current.applicationContext as MyApp
    val photoDao = app.database.photoDao()
    val rows by indicatorVM.state.collectAsState()
    val loading by indicatorVM.loading.collectAsState()
    val error by indicatorVM.error.collectAsState()
    val hScroll = rememberScrollState()
    val indicatorCode by indicatorVM.indicatorCode.collectAsState()
    val indicatorName by indicatorVM.indicatorName.collectAsState()

    Log.d("indicator response rowsss", rows.toString())

    val photoVM: PhotoViewModel = viewModel(
        factory = PhotoViewModelFactory(photoDao)
    )

    val photos by photoVM.photos.collectAsState()


    val locationText1 by remember(location) {
        mutableStateOf(
            location?.let {
                val lat = String.format("%.5f", it.latitude)
                val lng = String.format("%.5f", it.longitude)
                "$lat~$lng"
            } ?: "Fetching location…"
        )
    }

    val locationText by remember(location) {
        mutableStateOf(
            location?.let {
                getAddressFromLocation(context, it.latitude, it.longitude)
            } ?: "Unknown location"
        )
    }


    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        )
        { granted ->
            if (granted) {
                @SuppressLint("MissingPermission")
                fusedClient.lastLocation.addOnSuccessListener {
                    location = it // the same as (loc -> location = loc)
                }
            }
        }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {

    LaunchedEffect(Unit) {
//       delay(500)

        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)

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
                    modifier = Modifier.fillMaxSize()  .padding(padding).background(color = Color( 0xffe8eae5)),
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

                        Box(
                            modifier = Modifier
                                .fillMaxWidth().background(Color(0xFFDDDDDD))
                                .padding(top = 0.dp, start = 5.dp , end = 5.dp),

                        ) {
                            Text(
                                text = "Indicator Code: " ,
                                fontSize = 16.sp,
                            )}
                        Box(
                            modifier = Modifier
                                .fillMaxWidth().background(Color(0xFFDDDDDD))
                                .padding(top = 0.dp, start = 5.dp , end = 5.dp),


                        ) {
                            Text(
                                text = "$indicatorCode" ,
                                fontSize = 16.sp,
                            )}
                        Box(
                            modifier = Modifier
                                .fillMaxWidth().background(Color(0xFFDDDDDD))
                                .padding(top = 0.dp, start = 5.dp , end = 5.dp),

                            ) {
                            Text(
                                text = "Indicator Name: " ,
                                fontSize = 16.sp,
                            )}
                        Box(
                            modifier = Modifier
                                .fillMaxWidth().background(Color(0xFFDDDDDD))
                                .padding(top = 0.dp , start = 5.dp , end = 5.dp),


                            ) {
                            Text(
                                text = "$indicatorName" ,
                                fontSize = 16.sp,
                            )}
                        // Table header sticky
                        IndicatorHeader(hScroll)

                    }


                    when {

                        loading -> item {
                            Text(
                                "Loading records...",
                                modifier = Modifier.padding(16.dp)
                            )
                        }

                        error != null -> item {
                            Text(
                                "Error: $error",
                                modifier = Modifier.padding(16.dp)
                            )
                        }

                        rows.isEmpty() -> item {
                            Text(
                                "No records found",
                                modifier = Modifier.padding(16.dp)
                            )
                        }

                        else -> {


                            items(rows) { row ->
                                IndicatorTableRow(row, hScroll)  // ✅ body shares same scroll
                                Divider()
                            }
                        }
                    }
                }
            }
        }




    }



}


