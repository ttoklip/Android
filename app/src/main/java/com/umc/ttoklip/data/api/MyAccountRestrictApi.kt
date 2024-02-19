package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.mypage.RestrictedResponse
import retrofit2.Response
import retrofit2.http.GET

interface MyAccountRestrictApi {
    @GET("/api/v1/my-page/restricted")
    suspend fun getRestrictedReason(): Response<ResponseBody<RestrictedResponse>>
}