package uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.FacebookAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // 🔹 Auth state exposed to UI
    private val _authState = MutableStateFlow<AuthResultState>(AuthResultState.Idle)
    val authState: StateFlow<AuthResultState> = _authState

    // EMAIL + PASSWORD REGISTER
    fun registerWithEmail(
        email: String,
        password: String
    ) {
        _authState.value = AuthResultState.Loading
        if (email.isBlank() || password.isBlank()) {
            _authState.value =
                AuthResultState.Error("Please enter Email and password")
        }else {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _authState.value = AuthResultState.Success
                    } else {
                        _authState.value =
                            AuthResultState.Error(task.exception?.message ?: "Registration failed")
                    }
                }
        }
    }

    // EMAIL + PASSWORD LOGIN
    fun loginWithEmail(
        email: String,
        password: String,onResult: (Boolean, String?) -> Unit
    ) {
        _authState.value = AuthResultState.Loading
        if (email.isBlank() || password.isBlank()) {
            _authState.value =
                uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel.AuthResultState.Error("Please enter Email and password")
        }else {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthResultState.Success
                    onResult(true, null)
                } else {
                    _authState.value =
                        AuthResultState.Error(task.exception?.message ?: "Login failed")
                    onResult(false, null)
                }
            }
        }
    }

    // GOOGLE LOGIN
    fun loginWithGoogle(idToken: String,onResult: (Boolean, String?) -> Unit) {
        _authState.value = AuthResultState.Loading

        val credential = GoogleAuthProvider.getCredential(idToken, null)
        signInWithCredential(credential,onResult)
    }

    // FACEBOOK LOGIN
//    fun loginWithFacebook(accessToken: String) {
//        _authState.value = AuthResultState.Loading
//
//        val credential = FacebookAuthProvider.getCredential(accessToken)
//        signInWithCredential(credential)
//    }



    // SHARED CREDENTIAL HANDLER
    private fun signInWithCredential(
        credential: AuthCredential,
        onResult: (Boolean, String?) -> Unit
    ) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthResultState.Success
                    onResult(true, null)
                } else {
                    val msg = task.exception?.message ?: "Authentication failed"
                    _authState.value = AuthResultState.Error(msg)
                    onResult(false, msg)
                }
            }
    }

    // SESSION CHECK (PERSISTENCE)
    fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    fun getCurrentUserEmail(): String? {
        return auth.currentUser?.email
    }

    // LOGOUT
    fun logout() {
        auth.signOut()
        _authState.value = AuthResultState.Idle
    }
}

// AUTH STATE MODEL
sealed class AuthResultState {
    object Idle : AuthResultState()
    object Loading : AuthResultState()
    object Success : AuthResultState()
    data class Error(val message: String) : AuthResultState()
}
