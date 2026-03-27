package uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uk.ac.tees.mad.E4621366.mobileappdevelopment.repositories.IndicatorRepository


class IndicatorViewModelFactory(
    private val repository: IndicatorRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(IndicatorViewModel::class.java)) {
            return IndicatorViewModel(repository) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }
}
