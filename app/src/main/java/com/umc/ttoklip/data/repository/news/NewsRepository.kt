package com.umc.ttoklip.data.repository.news

import com.umc.ttoklip.data.model.news.MainNewsResponse
import com.umc.ttoklip.module.NetworkResult

interface NewsRepository {

    suspend fun getNewsMain(): NetworkResult<MainNewsResponse>
}