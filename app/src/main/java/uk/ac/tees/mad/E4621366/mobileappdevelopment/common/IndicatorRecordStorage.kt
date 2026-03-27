package uk.ac.tees.mad.E4621366.mobileappdevelopment.common

import androidx.room.*
import androidx.room.Query
import org.jetbrains.annotations.NotNull

@Entity(tableName = "indicator_record_table")
data class IndicatorRecordEntity(
    @PrimaryKey
    val Id: String,
    val IndicatorCode: String,
    val SpatialDim: String?,
    val TimeDim: String?,
    val Dim1: String?,
    val Dim2: String?,
    val Dim3: String?,
    val Value: String?,
    val NumericValue: Double?,
    val CreatedAt: Long = System.currentTimeMillis()
)





@Dao
interface IndicatorRecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<IndicatorRecordEntity>)

//    @Query("SELECT * FROM indicator_record_table WHERE :code")
    //  suspend fun getIndicatorRecord(code: String?): List<IndicatorRecordEntity>

    @Query(
        """
    SELECT * FROM indicator_record_table
WHERE TRIM(IndicatorCode) = TRIM(:indicatorCode)
AND (:countryCode IS NULL OR TRIM(SpatialDim) = TRIM(:countryCode))
AND (:year IS NULL OR TRIM(TimeDim) LIKE TRIM(:year) || '%')
"""
    )
    suspend fun getIndicatorRecord(
        indicatorCode: String,
        countryCode: String?,
        year: String?
    ): List<IndicatorRecordEntity>

    @Query("DELETE FROM indicator_record_table WHERE 1")
    suspend fun clearIndicatorRecord()

    @Query("SELECT * FROM indicator_record_table")
    suspend fun getAllIndicatorRecord(): List<IndicatorRecordEntity>
}


//
//
