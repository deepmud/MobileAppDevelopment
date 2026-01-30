package uk.ac.tees.mad.E4621366.mobileappdevelopment.screen

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.collections.emptyList


class PhotoViewModel(
    private val dao: PhotoDao
) : ViewModel() {

    val photos = dao.getAllPhotos()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )


    fun savePhoto(context: Context, bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            val path = saveBitmapToInternalStorage(context, bitmap)
            dao.insert(PhotoEntity(imagePath = path))
        }
    }
}


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
