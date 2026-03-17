//package uk.ac.tees.mad.E4621366.mobileappdevelopment.screen
//
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
////import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import kotlinx.coroutines.delay
//import uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel.AuthViewModel
//
//@Composable
//fun IndicatorScreen(
//    navController: NavController,
//    indicatorVM: IndicatorViewModel = viewModel()
//) {
//    val context = LocalContext.current
//
//    // Example dropdown options
//    val prevalences = listOf("HEPATITIS_B", "HEPATITIS_C")
//    val countries = listOf("NG", "US", "UK")
//    val ages = listOf("0-4", "5-14", "15-24", "25-64")
//    val sexes = listOf("Male", "Female", "Both")
//    val years = (2000..2025).map { it.toString() }
//
//    var selectedPrevalence by remember { mutableStateOf(prevalences.first()) }
//    var selectedCountry by remember { mutableStateOf(countries.first()) }
//    var selectedAge by remember { mutableStateOf(ages.first()) }
//    var selectedSex by remember { mutableStateOf(sexes.first()) }
//    var selectedYear by remember { mutableStateOf(years.last()) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.spacedBy(12.dp)
//    ) {
//        Text("Select Indicator Filters", fontWeight = FontWeight.Bold, fontSize = 20.sp)
//
//        // Prevalence dropdown
//        DropdownMenuWithLabel(
//            label = "Prevalence",
//            options = prevalences,
//            selectedOption = selectedPrevalence,
//            onOptionSelected = { selectedPrevalence = it }
//        )
//
//        // Country dropdown
//        DropdownMenuWithLabel(
//            label = "Country",
//            options = countries,
//            selectedOption = selectedCountry,
//            onOptionSelected = { selectedCountry = it }
//        )
//
//        // Age dropdown
//        DropdownMenuWithLabel(
//            label = "Age",
//            options = ages,
//            selectedOption = selectedAge,
//            onOptionSelected = { selectedAge = it }
//        )
//
//        // Sex dropdown
//        DropdownMenuWithLabel(
//            label = "Sex",
//            options = sexes,
//            selectedOption = selectedSex,
//            onOptionSelected = { selectedSex = it }
//        )
//
//        // Year dropdown
//        DropdownMenuWithLabel(
//            label = "Year",
//            options = years,
//            selectedOption = selectedYear,
//            onOptionSelected = { selectedYear = it }
//        )
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        Button(
//            onClick = {
//                // Call your ViewModel to fetch indicators
//                indicatorVM.loadIndicators(
//                    indicator = selectedPrevalence,
//                    country = selectedCountry,
//                    year = selectedYear.toInt()
//                )
//            },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Fetch Data")
//        }
//    }
//}
