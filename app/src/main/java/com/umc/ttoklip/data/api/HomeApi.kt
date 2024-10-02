package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.home.HomeResponse
import com.umc.ttoklip.data.model.home.NotificationResponse
import com.umc.ttoklip.data.model.mypage.UserStreetResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {

    @GET("/api/v1/home")
    suspend fun getHomeMainApi(): Response<ResponseBody<HomeResponse>>

    @GET("/api/v1/notification/my-notification")
    suspend fun getNotifications(
        @Query("notificationCategory") category: String
    ): Response<ResponseBody<NotificationResponse>>

    @GET("/api/v1/member/street")
    suspend fun getStreet(): Response<ResponseBody<UserStreetResponse>>

}