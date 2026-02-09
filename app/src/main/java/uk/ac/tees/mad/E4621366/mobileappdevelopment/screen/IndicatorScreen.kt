package uk.ac.tees.mad.E4621366.mobileappdevelopment.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import uk.ac.tees.mad.E4621366.mobileappdevelopment.MyApp
import uk.ac.tees.mad.E4621366.mobileappdevelopment.component.DropdownMenuWithLabel
import uk.ac.tees.mad.E4621366.mobileappdevelopment.repositories.IndicatorRepository
import uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel.IndicatorViewModel
import uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel.IndicatorViewModelFactory

@Composable
fun IndicatorScreen(
    navController: NavController,
   indicatorVM: IndicatorViewModel
) {


    val context = LocalContext.current
    val prevalences by indicatorVM.prevalenceOptions.collectAsState()
    val countries by indicatorVM.countryOptions.collectAsState()
    val loader by indicatorVM.loader.collectAsState()


    val years = buildMap<String?, String> {
        put(null, "select")
        for (y in 1980..2025) put(y.toString(), y.toString())
    }


    var selectedPrevalence: String? by remember { mutableStateOf<String?>(null) }
    var selectedCountry: String? by remember { mutableStateOf<String?>(null) }

    var selectedYear: String? by remember { mutableStateOf(null) }

    Column(
        modifier = Modifier.fillMaxWidth().background(Color(0xFFE8EAE5))
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),

    ) {
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color(0xFFE8EAE5)))

        Text("W.H.O. Hepatitis Global Health Observatory Indicator Filters", fontWeight = FontWeight.Bold, fontSize = 20.sp)

        DropdownMenuWithLabel(
            label = "Prevalence",
            options = prevalences,
            selectedKey = selectedPrevalence,
            onOptionSelected = { selectedPrevalence = it }
        )

        DropdownMenuWithLabel(
            label = "Country",
            options = countries,
            selectedKey = selectedCountry,
            onOptionSelected = { selectedCountry = it }
        )

         // Year dropdown
        DropdownMenuWithLabel(
            label = "Year",
            options = years ,
            selectedKey = selectedYear,
            onOptionSelected = { selectedYear = it }

        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                val indicator = selectedPrevalence ?: run {
                    Toast.makeText(context, "Please select a prevalence", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                // Call your ViewModel to fetch indicators
                indicatorVM.loadIndicators(
                    indicator = selectedPrevalence!!,
                    country = selectedCountry,
                    year = selectedYear
                )

                navController.navigate("indicator_record")
            },
            modifier = Modifier.fillMaxWidth()
        )
        {
            Text("Fetch Data")
        }
        Log.d("adugaaaaaaaa", prevalences.isEmpty().toString())
        Log.d("ezeeeeeeeeeee", countries.isEmpty().toString())

        when {
            loader -> {
                CircularProgressIndicator()
            }
            prevalences.isEmpty() && countries.isEmpty() -> {
                Text("No indicators available yet")
                Button(onClick = { indicatorVM.refreshFromApi() }) {
                    Text("Refresh from API")
                }
            }
        }

    }
}
