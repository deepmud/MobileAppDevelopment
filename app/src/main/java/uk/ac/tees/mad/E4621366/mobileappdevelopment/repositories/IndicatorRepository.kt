package uk.ac.tees.mad.E4621366.mobileappdevelopment.repositories

import android.util.Log
import uk.ac.tees.mad.E4621366.mobileappdevelopment.common.CountryDao
import uk.ac.tees.mad.E4621366.mobileappdevelopment.common.CountryDto
import uk.ac.tees.mad.E4621366.mobileappdevelopment.common.CountryEntity
import uk.ac.tees.mad.E4621366.mobileappdevelopment.common.GhoResponse
import uk.ac.tees.mad.E4621366.mobileappdevelopment.common.HealthApiService
import uk.ac.tees.mad.E4621366.mobileappdevelopment.common.IndicatorDao
import uk.ac.tees.mad.E4621366.mobileappdevelopment.common.IndicatorDto
import uk.ac.tees.mad.E4621366.mobileappdevelopment.common.IndicatorEntity
import uk.ac.tees.mad.E4621366.mobileappdevelopment.common.IndicatorRecordDao
import uk.ac.tees.mad.E4621366.mobileappdevelopment.common.IndicatorRecordEntity
import uk.ac.tees.mad.E4621366.mobileappdevelopment.common.IndicatorRow


class IndicatorRepository(
    private val api: HealthApiService, private val dao: IndicatorRecordDao,private val dao2: IndicatorDao,private val dao3: CountryDao
) {

    suspend fun fetchIndicators(
        indicatorCode:String,
        country: String?,
        year:String?,
//        filters: Map<String, String>
        filters: String?
    ): Result<List<IndicatorRow>> {
        return try {
            val response = api.getIndicatorData(indicatorCode, filters)
            val entities = response.value.map {
                IndicatorRecordEntity(
                    Id = it.Id,
                    IndicatorCode = it.IndicatorCode,
                    SpatialDim = it.SpatialDim,
                    TimeDim = it.TimeDim,
                    Dim3 = it.Dim3,
                    Dim1 = it.Dim1,
                    Value = it.Value,
                    Dim2 = it.Dim2,
                    NumericValue = it.NumericValue

                )
            }

            //  SAVE TO ROOM
            //dao.clearIndicatorRecord()
           val dds = dao.insertAll(entities)
            Log.d("ababaa111111", dds.toString())
            Result.success(response.value)

        } catch (e: Exception) {
            Log.d("ababaa111dddd", "${indicatorCode}___${country}___${year}")
//                val cached =    dao.getAllIndicatorRecord2().map {

            val country2 = country?.trim().takeIf { !it.isNullOrBlank() && it.lowercase() != "select" && it.lowercase() != "null" }
            val year2 = year?.trim().takeIf { !it.isNullOrBlank() && it.lowercase() != "select" && it.lowercase() != "null" }

            val cached =    dao.getIndicatorRecord( indicatorCode,country2,year2).map {
                IndicatorRow(
                    Id = it.Id,
                    IndicatorCode = it.IndicatorCode,
                    SpatialDim = it.SpatialDim,
                    TimeDim = it.TimeDim,
                    Dim3 = it.Dim3,
                    Dim1 = it.Dim1,
                    Value = it.Value,
                    Dim2 = it.Dim2,
                    NumericValue = it.NumericValue,
                    Dim1Type = null,
                    Dim2Type = null,
                    Dim3Type = null
                )
            }
            Log.d("ababaaaaaa", cached.toString())
            if (cached.isEmpty())
                Result.failure(Exception("No offline data available"))

            else
                Result.success(cached)
        }
    }


    suspend fun fetchIndicatorName(indicatorCode:String,): Result<IndicatorDto> {
        return try{
            val cached =    dao2.getIndicatorName( indicatorCode).map {
                IndicatorDto(
                    IndicatorCode = it.IndicatorCode,
                    IndicatorName = it.IndicatorName,

                )
            }
            Result.success(cached.first())
        }  catch (e: Exception) {
                Result.failure(Exception("No offline data available"))

        }
    }

suspend fun fetchPrevalenceOptions(): Result<List<IndicatorDto>>  {
    return try{
        val response = api.getPrevalenceOptions()

        val entities = response.value.map {
            IndicatorEntity(
                IndicatorCode = it.IndicatorCode,
                IndicatorName = it.IndicatorName
            )
        }
        dao2.insertAll(entities)
       Result.success(response.value)
    }  catch (e: Exception) {

        val cached =  dao2.getIndicator().map {
            IndicatorDto(
                IndicatorCode = it.IndicatorCode,
                IndicatorName = it.IndicatorName
            )
        }
        if (cached.isEmpty())
            Result.failure(Exception("No offline data available"))

        else
            Result.success(cached)
    }
 }


        suspend fun fetchCountryOptions(): Result<List<CountryDto>> {
            return try{
                val response = api.getCountryOptions()
                Log.d("indicatorssssss22222222", response.toString())
                val entities = response.value.map {
                    CountryEntity(
                        CountryCode = it.CountryCode,
                        CountryName = it.CountryName
                    )
                }
                dao3.insertAll(entities)
                Result.success(response.value)
            }  catch (e: Exception) {
                Log.d("indicatorssssss2223333", e.message.toString())
               val cached = dao3.getCountry().map {
                    CountryDto(
                        CountryCode = it.CountryCode,
                        CountryName = it.CountryName
                    )
                }
                if (cached.isEmpty())
                    Result.failure(Exception("No offline data available"))

                else
                    Result.success(cached)

            }
        }
    }




