package com.umc.ttoklip.data.repository.news

import com.umc.ttoklip.data.model.CommonResponse
import com.umc.ttoklip.data.model.news.MainNewsResponse
import com.umc.ttoklip.data.model.news.NewsPageResponse
import com.umc.ttoklip.data.model.news.ReportRequest
import com.umc.ttoklip.data.model.news.comment.NewsCommentRequest
import com.umc.ttoklip.data.model.news.detail.NewsDetailResponse
import com.umc.ttoklip.module.NetworkResult
import retrofit2.http.Query

interface NewsRepository {

    suspend fun getNewsMain(): NetworkResult<MainNewsResponse>

    suspend fun getNewsPage(category: String, page: Int): NetworkResult<NewsPageResponse>

    suspend fun getDetailNews(postId :Int): NetworkResult<NewsDetailResponse>
    suspend fun postCommentNews(postId: Int, request : NewsCommentRequest): NetworkResult<CommonResponse>

    suspend fun postReportNews(postId: Int, request : ReportRequest): NetworkResult<CommonResponse>

    suspend fun postReportCommentNews(postId: Int, request : ReportRequest): NetworkResult<CommonResponse>

    suspend fun deleteCommentNews(postId: Int): NetworkResult<CommonResponse>

    suspend fun postLikeNews(postId: Int): NetworkResult<CommonResponse>

    suspend fun deleteLikeNews(postId: Int): NetworkResult<CommonResponse>

    suspend fun postScrapNews(postId: Int): NetworkResult<CommonResponse>

    suspend fun deleteScrapNews(postId: Int): NetworkResult<CommonResponse>

}