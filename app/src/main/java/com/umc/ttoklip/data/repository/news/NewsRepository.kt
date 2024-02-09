package com.umc.ttoklip.data.repository.news

import com.umc.ttoklip.data.model.CommonResponse
import com.umc.ttoklip.data.model.news.MainNewsResponse
import com.umc.ttoklip.data.model.news.comment.NewsCommentRequest
import com.umc.ttoklip.data.model.news.detail.NewsDetailResponse
import com.umc.ttoklip.module.NetworkResult

interface NewsRepository {

    suspend fun getNewsMain(): NetworkResult<MainNewsResponse>
    suspend fun getDetailNews(postId :Int): NetworkResult<NewsDetailResponse>
    suspend fun postCommentNews(postId: Int, request : NewsCommentRequest): NetworkResult<CommonResponse>
}