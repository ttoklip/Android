package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.honeytip.CreateHoneyTipResponse
import com.umc.ttoklip.data.model.mypage.MyPageInfoResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MyPage2Api {
    @GET("/api/v1/my-page")
    suspend fun getMyPageInfo(): Response<ResponseBody<MyPageInfoResponse>>

    @Multipart
    @POST("/api/v1/my-page/edit")
    suspend fun editMyPageInfo(
        @Part("street") street: RequestBody,
        @Part("nickname") nickname: RequestBody,
        @Part("categories") categories: RequestBody,
        @Part("profileImage") profileImage: MultipartBody.Part,
        @Part("independentYear") independentYear: RequestBody,
        @Part("independentMonth") independentMonth: RequestBody
    ): Response<ResponseBody<CreateHoneyTipResponse>>
}