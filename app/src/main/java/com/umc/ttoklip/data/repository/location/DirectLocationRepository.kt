package com.umc.ttoklip.data.repository.location

import com.umc.ttoklip.data.model.KakaoResponse
import retrofit2.Call

interface DirectLocationRepository {
    suspend fun getDirectAddress(address:String): Call<KakaoResponse.ResultSearchKeyword>
}