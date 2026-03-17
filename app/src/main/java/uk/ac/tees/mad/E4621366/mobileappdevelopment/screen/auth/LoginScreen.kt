package uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.auth

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import uk.ac.tees.mad.E4621366.mobileappdevelopment.R
import uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel.AuthViewModel
import androidx.compose.ui.res.stringResource
import uk.ac.tees.mad.E4621366.mobileappdevelopment.util.rememberGoogleSignIn
import uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel.AuthResultState

@Composable
fun LoginScreen(navController: NavController, authVM: AuthViewModel) {
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    val googleSignIn = rememberGoogleSignIn(authVM, navController, errorState = remember { mutableStateOf(null) })
    val authState by authVM.authState.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text("Login", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                authVM.loginWithEmail(email, password) { success, msg ->
                    if (success) navController.navigate("dashboard") {
                        popUpTo("login") { inclusive = true }
                    } else error = msg
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { googleSignIn.launcher.launch(googleSignIn.client.signInIntent) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text("Continue with Google")
        }

        Spacer(modifier = Modifier.height(8.dp))

        when (authState) {
            is AuthResultState.Loading -> {
                CircularProgressIndicator()
            }

            is AuthResultState.Success -> {
                LaunchedEffect(Unit) {
                    navController.navigate("dashboard") {
                        popUpTo("register") { inclusive = true }
                    }
                }
            }

            is AuthResultState.Error -> {
                Text(
                    text = (authState as AuthResultState.Error).message,
                    color = MaterialTheme.colorScheme.error
                )
            }

            else -> {}
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { navController.navigate("register") }) {
            Text("New user? Register")
        }
    }
}

