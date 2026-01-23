package uk.ac.tees.mad.E4621366.mobileappdevelopment.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.DashboardScreen
import uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.SplashScreen
import uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.auth.LoginScreen
import uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.auth.RegisterScreen

@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("dashboard") { DashboardScreen() }
    }
}

