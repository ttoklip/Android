package com.umc.ttoklip.data.repository.home

import com.umc.ttoklip.data.model.home.HomeResponse
import com.umc.ttoklip.module.NetworkResult

interface HomeRepository {

    suspend fun getHomeMain(): NetworkResult<HomeResponse>
}