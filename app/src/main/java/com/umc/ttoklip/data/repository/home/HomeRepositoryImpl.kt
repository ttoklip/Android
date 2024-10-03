package com.umc.ttoklip.data.repository.home

import com.umc.ttoklip.data.api.HomeApi
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.home.HomeResponse
import com.umc.ttoklip.data.model.home.NotificationResponse
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val api: HomeApi
) : HomeRepository {


    override suspend fun getHomeMain(): NetworkResult<HomeResponse> {
        return handleApi({api.getHomeMainApi()}) {response: ResponseBody<HomeResponse> -> response.result}
    }

    override suspend fun getNotifications(category: String): NetworkResult<NotificationResponse> {
        return handleApi({api.getNotifications(category = category)}) {response: ResponseBody<NotificationResponse> -> response.result}

    }
}