package com.umc.ttoklip.data.repository.mypage

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.honeytip.CreateHoneyTipResponse
import com.umc.ttoklip.data.model.mypage.MyPageInfoResponse
import com.umc.ttoklip.module.NetworkResult
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Part

interface MyPageRepository2 {
    suspend fun getMyPageInfo(): NetworkResult<MyPageInfoResponse>
    suspend fun editMyPageInfo(
        street: RequestBody,
        nickname: RequestBody,
        categories: RequestBody,
        profileImage: MultipartBody.Part,
        independentYear: RequestBody,
        independentMonth: RequestBody
    ): NetworkResult<CreateHoneyTipResponse>
}