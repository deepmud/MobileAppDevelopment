package uk.ac.tees.mad.E4621366.mobileappdevelopment.model


import retrofit2.http.GET

interface WhoApi {

    @GET("Indicator")
    suspend fun getIndicators(): IndicatorResponse
}
