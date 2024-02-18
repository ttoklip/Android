package com.umc.ttoklip.data.repository.news

import com.umc.ttoklip.data.api.NewsApi
import com.umc.ttoklip.data.model.CommonResponse
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.news.MainNewsResponse
import com.umc.ttoklip.data.model.news.NewsPageResponse
import com.umc.ttoklip.data.model.news.ReportRequest
import com.umc.ttoklip.data.model.news.comment.NewsCommentRequest
import com.umc.ttoklip.data.model.news.detail.NewsDetailResponse
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi
): NewsRepository {

    override suspend fun getNewsMain(): NetworkResult<MainNewsResponse> {
        return handleApi({api.getNewsMainApi()}) {response: ResponseBody<MainNewsResponse> -> response.result}
    }

    override suspend fun getNewsPage(category: String, page: Int): NetworkResult<NewsPageResponse> {
        return handleApi({api.getNewsPageApi(category = category, page = page)}) {response: ResponseBody<NewsPageResponse> -> response.result}
    }

    override suspend fun getDetailNews(postId: Int): NetworkResult<NewsDetailResponse> {
        return handleApi({api.getDetailNewsApi(postId = postId)}) {response: ResponseBody<NewsDetailResponse> -> response.result}
    }

    override suspend fun postCommentNews(postId: Int, request : NewsCommentRequest): NetworkResult<CommonResponse> {
        return handleApi({api.postCommentNewsApi(postId = postId, request = request)}) {response: ResponseBody<CommonResponse> -> response.result}
    }

    override suspend fun postReportNews(
        postId: Int,
        request: ReportRequest
    ): NetworkResult<CommonResponse> {
        return handleApi({api.postReportNewsApi(postId = postId, request= request)}) {response: ResponseBody<CommonResponse> -> response.result}

    }

    override suspend fun postReportCommentNews(
        postId: Int,
        request: ReportRequest
    ): NetworkResult<CommonResponse> {
        return handleApi({api.postReportCommentNewsApi(commentId = postId, request= request)}) {response: ResponseBody<CommonResponse> -> response.result}
    }

    override suspend fun deleteCommentNews(postId: Int): NetworkResult<CommonResponse> {
        return handleApi({api.deleteCommentNewsApi(commentId = postId)}) {response: ResponseBody<CommonResponse> -> response.result}
    }

    override suspend fun postLikeNews(postId: Int): NetworkResult<CommonResponse> {
        return handleApi({api.postLikeNewsApi(postId = postId)}) {response: ResponseBody<CommonResponse> -> response.result}
    }

    override suspend fun deleteLikeNews(postId: Int): NetworkResult<CommonResponse> {
        return handleApi({api.deleteLikeNewsApi(postId = postId)}) {response: ResponseBody<CommonResponse> -> response.result}
    }

    override suspend fun postScrapNews(postId: Int): NetworkResult<CommonResponse> {
        return handleApi({api.postScrapNewsApi(postId = postId)}) {response: ResponseBody<CommonResponse> -> response.result}
    }

    override suspend fun deleteScrapNews(postId: Int): NetworkResult<CommonResponse> {
        return handleApi({api.deleteScrapNewsApi(postId = postId)}) {response: ResponseBody<CommonResponse> -> response.result}
    }

}