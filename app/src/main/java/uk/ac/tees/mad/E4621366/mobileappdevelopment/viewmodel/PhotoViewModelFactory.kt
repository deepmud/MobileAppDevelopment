package uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uk.ac.tees.mad.E4621366.mobileappdevelopment.common.PhotoDao




class PhotoViewModelFactory(
    private val dao: PhotoDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PhotoViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

