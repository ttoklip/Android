package com.umc.ttoklip.data.repository.search

import com.umc.ttoklip.data.model.CommonResponse
import com.umc.ttoklip.data.model.news.MainNewsResponse
import com.umc.ttoklip.data.model.news.comment.NewsCommentRequest
import com.umc.ttoklip.data.model.news.detail.NewsDetailResponse
import com.umc.ttoklip.data.model.search.NewsSearchResponse
import com.umc.ttoklip.data.model.search.SearchModel
import com.umc.ttoklip.module.NetworkResult

interface SearchRepository {

    suspend fun getNewsSearch(title : String): NetworkResult<List<SearchModel>>

    suspend fun getTipSearch(title : String): NetworkResult<List<SearchModel>>

    suspend fun getTownSearch(title : String): NetworkResult<List<SearchModel>>
}