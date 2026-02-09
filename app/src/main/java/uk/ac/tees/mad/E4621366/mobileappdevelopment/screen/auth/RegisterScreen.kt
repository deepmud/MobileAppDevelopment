package  uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.auth


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel.AuthResultState
import uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel.AuthViewModel


@Composable
fun RegisterScreen(
    navController: NavController,
    authVM: AuthViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authState by authVM.authState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text("Register", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { authVM.registerWithEmail(email, password) }
        ) {
            Text("Create Account")
        }

        Spacer(Modifier.height(16.dp))

        when (authState) {
            is AuthResultState.Loading -> {
                CircularProgressIndicator()
            }

            is AuthResultState.Success -> {
                LaunchedEffect(Unit) {
                    navController.navigate("login") {
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

        Spacer(Modifier.height(16.dp))

        TextButton(onClick = { navController.popBackStack() }) {
            Text("Already have an account? Login")
        }
    }
}

