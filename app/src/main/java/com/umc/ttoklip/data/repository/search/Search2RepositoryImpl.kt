package com.umc.ttoklip.data.repository.search

import com.umc.ttoklip.data.api.Search2Api
import com.umc.ttoklip.data.api.SearchApi
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.mypage.UserStreetResponse
import com.umc.ttoklip.data.model.search.NewsSearchResponse
import com.umc.ttoklip.data.model.search.SearchModel
import com.umc.ttoklip.data.model.search.TipSearchResponse
import com.umc.ttoklip.data.model.search.TownSearchResponse
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import javax.inject.Inject

class Search2RepositoryImpl @Inject constructor(
    private val api: Search2Api
) : Search2Repository {


    override suspend fun getNewsSearch(
        title: String,
        sort: String,
        page: Int
    ): NetworkResult<NewsSearchResponse> {
        return handleApi({
            api.getSearchNewsApi(
                title = title,
                sort = sort,
                page =page
            )
        }) { response: ResponseBody<NewsSearchResponse> ->
            response.result
        }
    }

    override suspend fun getTipSearch(
        title: String,
        sort: String,
        page: Int
    ): NetworkResult<TipSearchResponse> {
        return handleApi({
            api.getSearchTipApi(
                title = title,
                sort = sort,
                page =page
            )
        }) { response: ResponseBody<TipSearchResponse> ->
            response.result

        }
    }

    override suspend fun getTownSearch(
        title: String,
        sort: String,
        page: Int
    ): NetworkResult<TownSearchResponse> {
        return handleApi({
            api.getSearchTownApi(
                title = title,
                sort = sort,
                page =page
            )
        }) { response: ResponseBody<TownSearchResponse> ->
            response.result
        }

    }

    override suspend fun getUserStreet(): NetworkResult<UserStreetResponse> {
        return handleApi({api.getStreet()}) {response: ResponseBody<UserStreetResponse> -> response.result}
    }

}