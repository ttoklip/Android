package com.umc.ttoklip.data.repository.location

import com.umc.ttoklip.R
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.data.api.KakaoApi
import com.umc.ttoklip.data.model.KakaoResponse
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.login.LoginResponse
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import retrofit2.Call
import javax.inject.Inject

class DirectLocationRepositoryImpl @Inject constructor(
    private val api: KakaoApi
): DirectLocationRepository {
    companion object{
        val api_key=TtoklipApplication.getString(R.string.kakao_api_key)
    }

    override suspend fun getDirectAddress(address: String): NetworkResult<KakaoResponse.ResultSearchKeyword> {
        return handleApi({api.getSearchKeyword(api_key,address)}){response: ResponseBody<KakaoResponse.ResultSearchKeyword> ->response.result}
    }
}