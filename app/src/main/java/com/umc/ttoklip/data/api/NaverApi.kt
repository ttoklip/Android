package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.naver.geocoding.GeocodingResponse
import com.umc.ttoklip.data.model.naver.reversegeocoding.ReverseGeocodingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverApi {
    @GET("map-geocode/v2/geocode")
    suspend fun fetchGeocoding(
        @Query("query") query: String
    ): Response<GeocodingResponse>

    @GET("map-reversegeocode/v2/gc")
    suspend fun getReverseGeocodingInfo(
        @Query("coords") coords: String,
        @Query("output") output: String,
        @Query("orders") orders: String = "addr",
    ): Response<ReverseGeocodingResponse>
}