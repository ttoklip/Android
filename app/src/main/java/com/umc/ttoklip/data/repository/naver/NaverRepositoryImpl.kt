package com.umc.ttoklip.data.repository.naver

import com.umc.ttoklip.data.api.NaverApi
import com.umc.ttoklip.data.model.naver.GeocodingResponse
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import javax.inject.Inject

class NaverRepositoryImpl @Inject constructor(
    private val api: NaverApi
) : NaverRepository  {
    override suspend fun fetchGeocoding(query: String): NetworkResult<GeocodingResponse> {
        return handleApi({api.fetchGeocoding(query)}) {response: GeocodingResponse -> response}
    }

}