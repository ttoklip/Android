package com.umc.ttoklip.data.repository.location

import com.umc.ttoklip.data.model.KakaoResponse
import com.umc.ttoklip.module.NetworkResult

interface DirectLocationRepository {
    suspend fun getDirectAddress(address:String): NetworkResult<KakaoResponse.ResultSearchKeyword>
}