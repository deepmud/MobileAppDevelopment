package uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import uk.ac.tees.mad.E4621366.mobileappdevelopment.common.PhotoDao
import uk.ac.tees.mad.E4621366.mobileappdevelopment.common.PhotoEntity
import uk.ac.tees.mad.E4621366.mobileappdevelopment.common.saveBitmapToInternalStorage
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
            dao.clearPhoto();
            dao.insert(PhotoEntity(imagePath = path))
        }
    }
}

