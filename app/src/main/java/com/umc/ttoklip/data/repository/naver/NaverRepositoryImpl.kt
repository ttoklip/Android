package com.umc.ttoklip.data.repository.naver

import com.umc.ttoklip.data.api.NaverApi
import com.umc.ttoklip.data.model.naver.geocoding.GeocodingResponse
import com.umc.ttoklip.data.model.naver.reversegeocoding.ReverseGeocodingResponse
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import javax.inject.Inject

class NaverRepositoryImpl @Inject constructor(
    private val api: NaverApi
) : NaverRepository  {
    override suspend fun fetchGeocoding(query: String): NetworkResult<GeocodingResponse> {
        return handleApi({api.fetchGeocoding(query)}) {response: GeocodingResponse -> response}
    }

    override suspend fun fetchReverseGeocodingInfo(
        coords: String,
        output: String
    ): NetworkResult<ReverseGeocodingResponse> {
        return handleApi({api.getReverseGeocodingInfo(coords, output)}) {response: ReverseGeocodingResponse -> response}
    }

    override suspend fun getAdmcode(coords: String): NetworkResult<ReverseGeocodingResponse> {
        return handleApi({api.getAdmcode(coords)}){response: ReverseGeocodingResponse->response}
    }

}