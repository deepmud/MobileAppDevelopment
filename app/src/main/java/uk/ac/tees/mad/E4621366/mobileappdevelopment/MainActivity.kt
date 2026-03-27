package uk.ac.tees.mad.E4621366.mobileappdevelopment

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import uk.ac.tees.mad.E4621366.mobileappdevelopment.navigation.AppNav
import uk.ac.tees.mad.E4621366.mobileappdevelopment.ui.theme.MobileAppDevelopmentTheme
import androidx.core.graphics.toColorInt

class MainActivity : ComponentActivity() {
   // window.statusBarColor = android.graphics.Color.BLACK

    override fun onCreate(savedInstanceState: Bundle?) {
//        window.statusBarColor = android.graphics.Color.BLACK
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                scrim = Color.BLACK
            ),
                    navigationBarStyle = SystemBarStyle.light(
                    scrim = "#1e3c65".toColorInt(), // ✅ bar below your footer
            darkScrim = "#1e3c65".toColorInt()
        )
        )
        setContent {
            MobileAppDevelopmentTheme {
                AppNav()

            }
        }
    }
}
//
//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    MobileAppDevelopmentTheme {
//        AppNav()
//
//    }
//}

//setContent {
//            MobileAppDevelopmentTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
 //   MobileAppDevelopmentTheme {
 //       AppNav()

 //   }
//            Text("APP STARTED")
//}