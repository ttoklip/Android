package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.mypage.MyBlockUserResponse
import com.umc.ttoklip.data.model.mypage.NoticeResponse
import com.umc.ttoklip.data.model.mypage.RestrictedResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface MyPage3Api {
    @GET("/api/v1/my-page/restricted")
    suspend fun getRestrictedReason(): Response<ResponseBody<RestrictedResponse>>

    @GET("/api/v1/my-page/blocked")
    suspend fun getMyBlockedUser(): Response<ResponseBody<MyBlockUserResponse>>

    @DELETE("/api/v1/my-page/unblock/{targetId}")
    suspend fun deleteBlockUser(@Path("targetId") userId: Long): Response<ResponseBody<Unit>>

    @GET("api/v1/notice")
    suspend fun getNotices(): Response<ResponseBody<NoticeResponse>>
}