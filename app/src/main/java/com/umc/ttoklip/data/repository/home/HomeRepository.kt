package com.umc.ttoklip.data.repository.home

import com.umc.ttoklip.data.model.home.HomeResponse
import com.umc.ttoklip.data.model.home.NotificationResponse
import com.umc.ttoklip.data.model.mypage.UserStreetResponse
import com.umc.ttoklip.module.NetworkResult

interface HomeRepository {

    suspend fun getHomeMain(): NetworkResult<HomeResponse>

    suspend fun getNotifications(category: String) : NetworkResult<NotificationResponse>

    suspend fun getUserStreet() : NetworkResult<UserStreetResponse>
}