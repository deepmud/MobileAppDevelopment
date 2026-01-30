package uk.ac.tees.mad.E4621366.mobileappdevelopment.util

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import uk.ac.tees.mad.E4621366.mobileappdevelopment.R
import uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel.AuthViewModel

data class GoogleSignInResult(
    val launcher: androidx.activity.result.ActivityResultLauncher<android.content.Intent>,
    val client: com.google.android.gms.auth.api.signin.GoogleSignInClient
)

@Composable
fun rememberGoogleSignIn(
    authVM: AuthViewModel,
    navController: NavController,
    errorState: MutableState<String?> // pass error holder from screen
): GoogleSignInResult {
    val context = LocalContext.current
    val webClientId = androidx.compose.ui.res.stringResource(R.string.default_web_client_id)

    // Google Sign-In client
    val googleSignInClient = remember {
        GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(webClientId)
                .requestEmail()
                .build()
        )
    }

    // Launcher for Google Sign-In
    val googleLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken
            if (idToken != null) {
                authVM.loginWithGoogle(idToken) { success, msg ->
                    if (success) {
                        navController.navigate("dashboard") {
                            popUpTo("login") { inclusive = true }
                        }
                    } else {
                        errorState.value = msg
                    }
                }
            }
        } catch (e: ApiException) {
            e.printStackTrace()
            errorState.value = "Google login failed: ${e.message}"
        }
    }

    return GoogleSignInResult(launcher = googleLauncher, client = googleSignInClient)
}
