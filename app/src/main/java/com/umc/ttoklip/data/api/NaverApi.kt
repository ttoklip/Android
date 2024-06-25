package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.naver.GeocodingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverApi {
    @GET("map-geocode/v2/geocode")
    suspend fun fetchGeocoding(
        @Query("query") query: String
    ): Response<GeocodingResponse>
}