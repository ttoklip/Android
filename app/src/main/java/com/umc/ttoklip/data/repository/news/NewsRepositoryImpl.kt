package com.umc.ttoklip.data.repository.news

import com.umc.ttoklip.data.api.NewsApi
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.news.MainNewsResponse
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi
): NewsRepository {

    override suspend fun getNewsMain(): NetworkResult<MainNewsResponse> {
        return handleApi({api.getNewsMainApi()}) {response: ResponseBody<MainNewsResponse> -> response.result}
    }

}