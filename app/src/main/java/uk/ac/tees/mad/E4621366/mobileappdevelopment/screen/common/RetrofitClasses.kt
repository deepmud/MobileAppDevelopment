package uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.common


import androidx.room.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap

object RetrofitClient {

    private const val BASE_URL = "https://ghoapi.azureedge.net/api/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api: HealthApiService by lazy {
        retrofit.create(HealthApiService::class.java)
    }
}

interface HealthApiService {

    @GET("indicators")
    suspend fun getIndicators(
        @QueryMap filters: Map<String, String>
    ): ApiResponse<List<IndicatorDto>>
}


data class ApiResponse<T>(
    val data: T
)

data class IndicatorDto(
    val code: String,
    val name: String,
    val value: Double,
    val year: Int
)

object FilterKeys {
    const val INDICATOR = "indicator"
    const val COUNTRY = "country"
    const val YEAR = "year"
}



class FilterBuilder {

    private val filters = mutableMapOf<String, String>()

    fun indicator(code: String?) = apply {
        code?.let { filters[FilterKeys.INDICATOR] = it }
    }

    fun country(country: String?) = apply {
        country?.let { filters[FilterKeys.COUNTRY] = it }
    }

    fun year(year: Int?) = apply {
        year?.let { filters[FilterKeys.YEAR] = it.toString() }
    }

    fun build(): Map<String, String> = filters
}


class IndicatorRepository(
    private val api: HealthApiService
) {

    suspend fun fetchIndicators(
        filters: Map<String, String>
    ): Result<List<IndicatorDto>> {
        return try {
            val response = api.getIndicators(filters)
            Result.success(response.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
