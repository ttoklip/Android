package com.umc.ttoklip.data.repository.naver

import com.umc.ttoklip.data.model.naver.geocoding.GeocodingResponse
import com.umc.ttoklip.data.model.naver.reversegeocoding.ReverseGeocodingResponse
import com.umc.ttoklip.module.NetworkResult

interface NaverRepository {
    suspend fun fetchGeocoding(query: String): NetworkResult<GeocodingResponse>

    suspend fun fetchReverseGeocodingInfo(coords: String, output: String): NetworkResult<ReverseGeocodingResponse>
}