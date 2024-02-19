package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.honeytip.CreateHoneyTipResponse
import com.umc.ttoklip.data.model.mypage.MyPageInfoResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface MyPage2Api {
    @GET("/api/v1/my-page")
    suspend fun getMyPageInfo(): Response<ResponseBody<MyPageInfoResponse>>

    @Multipart
    @PATCH("/api/v1/my-page/edit")
    suspend fun editMyPageInfo(
        @Part profileImage: MultipartBody.Part?,
        @Part categories:List<MultipartBody.Part>,
        @PartMap params:MutableMap<String,RequestBody>
    ): Response<ResponseBody<CreateHoneyTipResponse>>
}