package  uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.sp
import uk.ac.tees.mad.E4621366.mobileappdevelopment.component.FormField
import uk.ac.tees.mad.E4621366.mobileappdevelopment.model.RegisterRequest

@Composable
fun RegisterScreen(navController: NavController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var town by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var county by remember { mutableStateOf("") }
    var postcode by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Text("Register", fontSize = 24.sp)

        Spacer(Modifier.height(12.dp))

        FormField("Email", email) { email = it }
        FormField("Password", password, true) { password = it }
        FormField("Gender", gender) { gender = it }
        FormField("Town", town) { town = it }
        FormField("City", city) { city = it }
        FormField("County", county) { county = it }
        FormField("Postcode", postcode) { postcode = it }
        FormField("country", country) { country = it }


        Spacer(Modifier.height(20.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {

                val request = RegisterRequest(
                    email,
                    password,
                    gender,
                    town,
                    city,
                    county,
                    country,
                    postcode,
                )

                // TODO: Send to API in Sprint 4
                navController.navigate("login")
            }
        ) {
            Text("Register")
        }
    }
}

