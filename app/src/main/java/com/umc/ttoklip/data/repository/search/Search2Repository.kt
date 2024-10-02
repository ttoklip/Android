package com.umc.ttoklip.data.repository.search

import com.umc.ttoklip.data.model.CommonResponse
import com.umc.ttoklip.data.model.mypage.UserStreetResponse
import com.umc.ttoklip.data.model.news.MainNewsResponse
import com.umc.ttoklip.data.model.news.comment.NewsCommentRequest
import com.umc.ttoklip.data.model.news.detail.NewsDetailResponse
import com.umc.ttoklip.data.model.search.NewsSearchResponse
import com.umc.ttoklip.data.model.search.SearchModel
import com.umc.ttoklip.data.model.search.TipSearchResponse
import com.umc.ttoklip.data.model.search.TownSearchResponse
import com.umc.ttoklip.module.NetworkResult

interface Search2Repository {

    suspend fun getNewsSearch(title : String, sort: String, page: Int): NetworkResult<NewsSearchResponse>

    suspend fun getTipSearch(title : String, sort: String, page: Int): NetworkResult<TipSearchResponse>

    suspend fun getTownSearch(title : String, sort: String, page: Int): NetworkResult<TownSearchResponse>

    suspend fun getUserStreet() : NetworkResult<UserStreetResponse>
}