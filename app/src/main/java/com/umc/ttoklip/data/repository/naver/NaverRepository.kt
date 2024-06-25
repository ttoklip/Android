package com.umc.ttoklip.data.repository.naver

import com.umc.ttoklip.data.model.naver.GeocodingResponse
import com.umc.ttoklip.module.NetworkResult

interface NaverRepository {
    suspend fun fetchGeocoding(query: String): NetworkResult<GeocodingResponse>
}