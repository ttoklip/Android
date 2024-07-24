package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.honeytip.CreateHoneyTipResponse
import com.umc.ttoklip.data.model.mypage.MyPageInfoResponse
import com.umc.ttoklip.data.model.signup.SignupResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Query

interface MyPage2Api {
    @GET("/api/v1/my-page")
    suspend fun getMyPageInfo(): Response<ResponseBody<MyPageInfoResponse>>

    @Multipart
    @PATCH("/api/v1/my-page/edit")
    suspend fun editMyPageInfo(
        @Part("street") street: RequestBody,
        @Part("nickname") nickname: RequestBody,
        @Part("categories") categories: RequestBody,
        @Part profileImage: MultipartBody.Part?,
        @Part("independentYear") independentYear: RequestBody,
        @Part("independentMonth") independentMonth: RequestBody
    ): Response<ResponseBody<CreateHoneyTipResponse>>

    @GET("/api/v1/privacy/local/check-nickname")
    suspend fun nickCheck(@Query("nickname") nickname:String)
            : Response<ResponseBody<SignupResponse>>
}