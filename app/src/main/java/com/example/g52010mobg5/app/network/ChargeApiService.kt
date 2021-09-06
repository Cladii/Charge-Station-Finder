package com.example.g52010mobg5.app.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.openchargemap.io/v3/poi/"
private const val BASE_REQUEST =
    "&output=json&countrycode=BE&maxresults=250&verbose=false&distance=10&distanceunit=KM"
private const val API_KEY = "4de576f2-1cda-44ef-9ffa-5918e529775b"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ChargeApiService {
    @GET("?key=$API_KEY$BASE_REQUEST")
    suspend fun getChargeStations(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): List<ChargeStation>

    @GET("?key=$API_KEY$BASE_REQUEST")
    suspend fun getChargeStations(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("connectiontypeid") connectiontypeid: Int
    ): List<ChargeStation>

}

object ChargeApi {
    val retrofitService: ChargeApiService by lazy { retrofit.create(ChargeApiService::class.java) }
}
