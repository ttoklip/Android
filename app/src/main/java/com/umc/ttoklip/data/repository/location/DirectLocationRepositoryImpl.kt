package com.umc.ttoklip.data.repository.location

import com.umc.ttoklip.R
import com.umc.ttoklip.data.api.KakaoApi
import com.umc.ttoklip.data.model.KakaoResponse
import retrofit2.Call
import javax.inject.Inject

class DirectLocationRepositoryImpl @Inject constructor(
    private val api: KakaoApi
): DirectLocationRepository {

    override suspend fun getDirectAddress(address: String): Call<KakaoResponse.ResultSearchKeyword> {
        val call = api.getSearchKeyword(R.string.kakao_api_key.toString(), address)
    return call
    }
}