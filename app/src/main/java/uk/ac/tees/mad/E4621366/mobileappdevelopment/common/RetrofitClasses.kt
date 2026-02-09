package uk.ac.tees.mad.E4621366.mobileappdevelopment.common


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
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

 val indicatorsFilter: String
     get() = "contains(IndicatorCode,'HBV') or contains(IndicatorCode,'HCV') or contains(IndicatorCode,'HEP')";

    @GET("{indicator}")
    suspend fun getIndicatorData(
        @Path("indicator") indicator: String?,
        @Query("\$filter", encoded = true) filter: String? = null,
        @Query("\$top") top: Int? = null,
        @Query("\$skip") skip: Int? = null
    ): GhoResponse<IndicatorRow>


    //  real path
    @GET("indicator")
    suspend fun getPrevalenceOptions(
        @Query("\$filter") filter: String? = indicatorsFilter,
        @Query("\$top") top: Int? = null,
        @Query("\$skip") skip: Int? = null
    ): GhoResponse<IndicatorDto>

    @GET("RegionCountry")
    suspend fun getCountryOptions(
        @Query("\$top") top: Int? = null,
        @Query("\$skip") skip: Int? = null
    ): GhoResponse<CountryDto>
}

data class GhoResponse<T>(
    val value: List<T>
)

data class IndicatorDto(
    val IndicatorCode: String,
    val IndicatorName: String,
)

data class CountryDto(
    val CountryCode: String,
    val CountryName: String?,
)

data class IndicatorRow(

    val Id: String,
    val IndicatorCode: String,
    val SpatialDim: String?,
    val TimeDim: String?,
    val Dim1Type: String?,
    val Dim1: String?,
    val Dim2Type: String?,
    val Dim2: String?,
    val Dim3Type: String?,
    val Dim3: String?,
    val Value: String?,
    val NumericValue: Double?
)


class FilterBuilder {
    private val parts = mutableListOf<String>()

    fun country(code: String?) = apply {
        code?.let { parts += "SpatialDim eq '$it'" }
    }

    fun year(year: String?) = apply {
        year?.let { parts += "TimeDim eq $it" }
    }

    fun build(): String? = parts.takeIf { it.isNotEmpty() }?.joinToString(" and ")
}



