package com.umc.ttoklip.data.repository.news

import com.umc.ttoklip.data.model.CreateHoneyTipRequest
import com.umc.ttoklip.data.model.CreateHoneyTipResponse
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.news.MainNewsResponse
import com.umc.ttoklip.module.NetworkResult

interface NewsRepository {

    suspend fun getNewsMain(): NetworkResult<MainNewsResponse>
}