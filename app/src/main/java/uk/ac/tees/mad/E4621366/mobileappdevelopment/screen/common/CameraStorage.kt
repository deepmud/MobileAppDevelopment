package uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.common

import android.content.Context
import android.graphics.Bitmap
import androidx.room.Database
import androidx.room.*
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.io.FileOutputStream

@Entity(tableName = "photos")
data class PhotoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val imagePath: String,
    val createdAt: Long = System.currentTimeMillis()
)

@Dao
interface PhotoDao {

    @Insert
    suspend fun insert(photo: PhotoEntity)

    @Query("SELECT * FROM photos ORDER BY createdAt DESC")
    fun getAllPhotos(): Flow<List<PhotoEntity>>
}


@Database(
    entities = [PhotoEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}


fun saveBitmapToInternalStorage(
    context: Context,
    bitmap: Bitmap
): String {
    val filename = "IMG_${System.currentTimeMillis()}.jpg"
    val file = File(context.filesDir, filename)

    FileOutputStream(file).use {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, it)
    }

    return file.absolutePath
}


