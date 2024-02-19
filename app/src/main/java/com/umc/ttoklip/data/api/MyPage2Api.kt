package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.mypage.MyPageInfo
import retrofit2.Response
import retrofit2.http.GET

interface MyPage2Api {
    @GET("/api/v1/my-page")
    suspend fun getMyPageInfo(): Response<ResponseBody<MyPageInfo>>
}