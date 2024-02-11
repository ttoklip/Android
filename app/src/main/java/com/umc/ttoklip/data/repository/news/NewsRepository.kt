package com.umc.ttoklip.data.repository.news

import com.umc.ttoklip.data.model.CommonResponse
import com.umc.ttoklip.data.model.news.MainNewsResponse
import com.umc.ttoklip.data.model.news.ReportRequest
import com.umc.ttoklip.data.model.news.comment.NewsCommentRequest
import com.umc.ttoklip.data.model.news.detail.NewsDetailResponse
import com.umc.ttoklip.module.NetworkResult

interface NewsRepository {

    suspend fun getNewsMain(): NetworkResult<MainNewsResponse>
    suspend fun getDetailNews(postId :Int): NetworkResult<NewsDetailResponse>
    suspend fun postCommentNews(postId: Int, request : NewsCommentRequest): NetworkResult<CommonResponse>

    suspend fun postReportNews(postId: Int, request : ReportRequest): NetworkResult<CommonResponse>

    suspend fun postReportCommentNews(postId: Int, request : ReportRequest): NetworkResult<CommonResponse>

}