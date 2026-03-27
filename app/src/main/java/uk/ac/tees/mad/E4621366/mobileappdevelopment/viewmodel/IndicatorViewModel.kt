package uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.E4621366.mobileappdevelopment.repositories.IndicatorRepository
import uk.ac.tees.mad.E4621366.mobileappdevelopment.common.FilterBuilder
import uk.ac.tees.mad.E4621366.mobileappdevelopment.common.IndicatorRow
import uk.ac.tees.mad.E4621366.mobileappdevelopment.common.RetrofitClient
import kotlin.collections.emptyList


class IndicatorViewModel(private val repository: IndicatorRepository) : ViewModel() {


    private val _state =
        MutableStateFlow<List<IndicatorRow>>(emptyList())
    val state: StateFlow<List<IndicatorRow>> = _state
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading


    private val _prevalenceOptions =  MutableStateFlow<Map<String?, String>>(emptyMap())
    val prevalenceOptions: StateFlow<Map<String?, String>> = _prevalenceOptions

    private val _prevalenceLoading = MutableStateFlow(false)
    val prevalenceLoading: StateFlow<Boolean> = _prevalenceLoading

    private val _prevalenceError = MutableStateFlow<String?>(null)
    val prevalenceError: StateFlow<String?> = _prevalenceError
    private val _countryOptions =  MutableStateFlow<Map<String?, String>>(emptyMap())
    val countryOptions: StateFlow<Map<String?, String>> = _countryOptions
    private val _countryLoading = MutableStateFlow(false)
    val countryLoading: StateFlow<Boolean> = _countryLoading

    private val _countryError = MutableStateFlow<String?>(null)
    val countryError: StateFlow<String?> = _countryError

    private val _loader = MutableStateFlow(false)
    val loader: StateFlow<Boolean> = _loader.asStateFlow()

    private val _indicatorName = MutableStateFlow<String?>(null)
    val indicatorName: StateFlow<String?> = _indicatorName.asStateFlow()

    private val _indicatorCode = MutableStateFlow<String?>(null)
    val indicatorCode: StateFlow<String?> = _indicatorCode.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error


    init {
        prefetchPrevalence()
        prefetchCountry()
    }
    fun refreshFromApi() {
        viewModelScope.launch {
            _loading.value = true
            prefetchCountry()
            prefetchPrevalence()
            _loading.value = false
        }
    }


    fun loadIndicators(
        indicator: String,
        country: String?,
        year: String?
    ) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val dto = repository.fetchIndicatorName(indicator)
             dto.onSuccess {
                 _indicatorCode.value = it.IndicatorCode
                 _indicatorName.value = it.IndicatorName
             }
                 .onFailure {
                     _indicatorCode.value = "no indicator"
                     _indicatorName.value = "no indicator"
                 }
            val filters = FilterBuilder()
                .country(country)
                .year(year)
                .build()


            try {
                val result = repository.fetchIndicators(indicator,country,year.toString(), filters)
                Log.d("indicator response", result.toString())

                result
                    .onSuccess {
                        _state.value = it
                    }
                    .onFailure {
                        _error.value = it.message
                        _state.value = emptyList()
                    }

            }catch (e: Exception) {
            Log.e("VM", "Unexpected exception", e)
            _error.value = e.message
            _state.value = emptyList()

        }  finally {
                _loading.value = false
            }
    }

    }


    fun prefetchCountry() {
        viewModelScope.launch {
            _countryLoading.value = true
            _countryError.value = null
            try {
                val result = repository.fetchCountryOptions()
                Log.d("indicatorssssss", result.toString())
                result.onSuccess { list ->
                    var i  =0;

                    _countryOptions.value = buildMap<String?, String> {
                        for (y in list.sortedBy { dto -> dto.CountryName }) put(y.CountryCode, ("${++i}.  ${y.CountryName ?: (y.CountryCode + " (country code)")}" ))
                    }
                    Log.d("_prevalenceOptions.value",  _countryOptions.value.toString())



                }
                    .onFailure { e ->
                        _countryError.value = e.message
                    }
            } finally {
                _countryLoading.value = false
//                _prevalenceOptions.value = buildMap<String?, String> {
//                    put(null, "select")
//                }
            }
        }
    }


    fun prefetchPrevalence() {
        viewModelScope.launch {
            _prevalenceLoading.value = true
            _prevalenceError.value = null
            try {
                val result = repository.fetchPrevalenceOptions()
                Log.d("indicatoraaaaaa", result.toString())
                result.onSuccess { list ->
                    var i  =0;
                    _prevalenceOptions.value = buildMap<String?, String> {
                        for (y in list.sortedBy { dto -> dto.IndicatorCode }) put(y.IndicatorCode,"${++i}.  ${y.IndicatorName}")
                    }
                    Log.d("_prevalenceOptions.value",  _prevalenceOptions.value.toString())

                }
                    .onFailure { e ->
                        _prevalenceError.value = e.message
                    }
            } finally {
                _prevalenceLoading.value = false
            }
        }
    }


}


