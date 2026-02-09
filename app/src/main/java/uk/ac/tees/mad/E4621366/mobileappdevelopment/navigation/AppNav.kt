package uk.ac.tees.mad.E4621366.mobileappdevelopment.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import uk.ac.tees.mad.E4621366.mobileappdevelopment.MyApp
import uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.DashboardScreen
import uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.IndicatorRecordScreen
import uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.SplashScreen
import uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.auth.LoginScreen
import uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.auth.RegisterScreen
import uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel.AuthViewModel
import uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.IndicatorScreen
import uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel.IndicatorViewModel
import uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel.IndicatorViewModelFactory


@Composable
fun AppNav() {
    val navController = rememberNavController()
    val authVM: AuthViewModel = viewModel()
    val app = LocalContext.current.applicationContext as MyApp
    val indicatorVM: IndicatorViewModel = viewModel(
        factory = IndicatorViewModelFactory(app.indicatorRepository)
    )
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("login") {
//            val authVM: AuthViewModel = viewModel();
            LoginScreen(
            navController,
            authVM
        )
        }
        composable("register") {
//            val authVM: AuthViewModel = viewModel();
            RegisterScreen(
            navController,
            authVM
        )
        }
        composable("dashboard") {
//            val authVM: AuthViewModel = viewModel();
            DashboardScreen(
            navController,
            authVM
        )
        }

        composable("indicator_page") {
//            val IndicatorVM: IndicatorViewModel = viewModel();
            IndicatorScreen(
            navController,indicatorVM
        )
        }

        composable("indicator_record") {
//            val authVM: AuthViewModel = viewModel();val IndicatorVM: IndicatorViewModel = viewModel();
            IndicatorRecordScreen(
            navController,
            authVM,indicatorVM
        )
        }
    }

}