package uk.ac.tees.mad.E4621366.mobileappdevelopment.common

import androidx.room.*
import androidx.room.Query

@Entity(tableName = "country_table")
data class CountryEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val CountryCode: String,
    val CountryName: String?,
    val CreatedAt: Long = System.currentTimeMillis()

)





@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<CountryEntity>)

    @Query("SELECT * FROM country_table")
    suspend fun getCountry(): List<CountryEntity>

    @Query("DELETE FROM country_table WHERE 1")
    suspend fun clearClear()
}




