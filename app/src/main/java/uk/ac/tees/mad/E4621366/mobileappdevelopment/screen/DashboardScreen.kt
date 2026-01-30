package uk.ac.tees.mad.E4621366.mobileappdevelopment.screen

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
//import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.delay
import uk.ac.tees.mad.E4621366.mobileappdevelopment.MyApp
import uk.ac.tees.mad.E4621366.mobileappdevelopment.R
import uk.ac.tees.mad.E4621366.mobileappdevelopment.util.rememberGoogleSignIn
import uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel.AuthViewModel

@Composable
fun DashboardScreen(navController: NavController, authVM: AuthViewModel = viewModel()) {

    val context = LocalContext.current

    val googleSignIn = rememberGoogleSignIn(authVM, navController, errorState = remember { mutableStateOf(null) })
    val fusedClient = LocationServices.getFusedLocationProviderClient(context)
    var location by remember { mutableStateOf<Location?>(null) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    val app = LocalContext.current.applicationContext as MyApp
    val photoDao = app.database.photoDao()

    val photoVM: PhotoViewModel = viewModel(
        factory = PhotoViewModelFactory(photoDao)
    )

    val photos by photoVM.photos.collectAsState()

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
        )
        { granted ->
            if (granted) {
                @SuppressLint("MissingPermission")
                fusedClient.lastLocation.addOnSuccessListener {
                    location = it // the same as (loc -> location = loc)
                }
            }
        }

    LaunchedEffect(Unit) {
        delay(500)

        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    )  {
        Button(onClick = {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }) {
            Text("Capture Image")
        }
        Text("Welcome to Dashboard")

        Button(onClick = {
            authVM.logout()
            // Sign out from Google client to force account picker
            googleSignIn.client.signOut()

            navController.navigate("login") {
                popUpTo("dashboard") { inclusive = true }
            }
        }) {
            Text("Logout")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment  = Alignment.End

    )  {
        location?.let {
            Text("Lat: ${it.latitude}")
            Text("Lng: ${it.longitude}")
        }
    }

    LazyColumn {
        items(photos) { photo ->
            Image(
                bitmap = BitmapFactory
                    .decodeFile(photo.imagePath)
                    .asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(8.dp)
            )
        }
    }

}
