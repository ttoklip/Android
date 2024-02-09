package com.umc.ttoklip.data.repository.search

import com.umc.ttoklip.data.api.SearchApi
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.search.NewsSearchResponse
import com.umc.ttoklip.data.model.search.SearchModel
import com.umc.ttoklip.data.model.search.TipSearchResponse
import com.umc.ttoklip.data.model.search.TownSearchResponse
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val api: SearchApi
): SearchRepository {


    override suspend fun getNewsSearch(title : String): NetworkResult<List<SearchModel>> {
        return handleApi({api.getSearchNewsApi(title = title)}) {response: ResponseBody<NewsSearchResponse> -> response.result.newsletters.map { it.toModel(1) }}
    }

    override suspend fun getTipSearch(title : String): NetworkResult<List<SearchModel>> {
        return handleApi({api.getSearchTipApi(title = title)}) {response: ResponseBody<TipSearchResponse> -> response.result.honeyTips.map { it.toModel(3) }}
    }

    override suspend fun getTownSearch(title: String): NetworkResult<List<SearchModel>> {
        return handleApi({api.getSearchTownApi(title = title)}) {response: ResponseBody<TownSearchResponse> -> response.result.communities.map { it.toModel("",5) }}

    }

}