package uk.ac.tees.mad.E4621366.mobileappdevelopment.common

import androidx.room.*
import androidx.room.Query
import org.jetbrains.annotations.NotNull

@Entity(tableName = "indicator_table")
data class IndicatorEntity(
    @PrimaryKey()
    val IndicatorCode: String,
    val IndicatorName: String,
    val CreatedAt: Long = System.currentTimeMillis()
)





@Dao
interface IndicatorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<IndicatorEntity>)

    @Query("SELECT * FROM indicator_table")
    suspend fun getIndicator(): List<IndicatorEntity>

    @Query("DELETE FROM indicator_table WHERE 1")
    suspend fun clearIndicator()

    @Query(
        """
    SELECT * FROM indicator_table
WHERE TRIM(IndicatorCode) = TRIM(:indicatorCode) LIMIT 1
"""
    )
    suspend fun getIndicatorName(
        indicatorCode: String,
    ): List<IndicatorEntity>
}




