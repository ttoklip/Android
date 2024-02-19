package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.mypage.MyBlockUserResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface MyBlockUserApi {
    @GET("/api/v1/my-page/blocked")
    suspend fun getMyBlockedUser(): Response<ResponseBody<MyBlockUserResponse>>

    @DELETE("/api/v1/my-page/unblock/{targetId}")
    suspend fun deleteBlockUser(@Path("targetId") userId: Long): Response<ResponseBody<Unit>>

}