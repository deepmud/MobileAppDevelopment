package uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.common.FilterBuilder
import uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.common.IndicatorDto
import uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.common.IndicatorRepository
import uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.common.PhotoDao
import uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.common.PhotoEntity
import uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.common.RetrofitClient
import uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.common.saveBitmapToInternalStorage
import kotlin.collections.emptyList


class IndicatorViewModel : ViewModel() {

    private val repository =
        IndicatorRepository(RetrofitClient.api)

    private val _state =
        MutableStateFlow<List<IndicatorDto>>(emptyList())
    val state: StateFlow<List<IndicatorDto>> = _state

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadIndicators(
        indicator: String?,
        country: String?,
        year: Int?
    ) {
        viewModelScope.launch {
            _loading.value = true

            val filters = FilterBuilder()
                .indicator(indicator)
                .country(country)
                .year(year)
                .build()

            val result = repository.fetchIndicators(filters)

            result
                .onSuccess { _state.value = it }
                .onFailure { _error.value = it.message }

            _loading.value = false
        }
    }
}

///*
//
//@Composable
//fun DashboardScreenjk(
//    vm: IndicatorViewModel = viewModel()
//) {
//    val data by vm.state.collectAsState()
//    val loading by vm.loading.collectAsState()
//    val error by vm.error.collectAsState()
//
//    LaunchedEffect(Unit) {
//        vm.loadIndicators(
//            indicator = "HEPATITIS_B",
//            country = "NG",
//            year = 2023
//        )
//    }
//
//    when {
//        loading -> Text("Loading…")
//        error != null -> Text("Error: $error")
//        else -> LazyColumn {
//            items(data) { item ->
//                Text("${item.name}: ${item.value}")
//            }
//        }
//    }
//}
//
//*/
