package uk.ac.tees.mad.E4621366.mobileappdevelopment.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel.AuthViewModel

@Composable
fun SplashScreen(navController: NavController) {
    val authVM: AuthViewModel = viewModel()

    LaunchedEffect(Unit) {
        delay(100)

        if (authVM.isLoggedIn()) {
            navController.navigate("dashboard") {
                popUpTo("splash") { inclusive = true }
            }
        } else {
            navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Emtech Health Record...", fontSize = 24.sp, color = Color(0xff1e3c65),fontStyle = FontStyle.Italic )
    }
}
