package com.umc.ttoklip.data.repository.search

import com.umc.ttoklip.data.api.SearchApi
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.search.NewsSearchResponse
import com.umc.ttoklip.data.model.search.SearchModel
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val api: SearchApi
): SearchRepository {


    override suspend fun getNewsSearch(title : String): NetworkResult<List<SearchModel>> {
        return handleApi({api.getSearchNewsApi(title = title)}) {response: ResponseBody<NewsSearchResponse> -> response.result.newsletters.map { it.toModel(1) }}
    }

}