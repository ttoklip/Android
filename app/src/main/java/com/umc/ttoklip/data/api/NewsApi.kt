package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.CommonResponse
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.news.MainNewsResponse
import com.umc.ttoklip.data.model.news.ReportRequest
import com.umc.ttoklip.data.model.news.comment.NewsCommentRequest
import com.umc.ttoklip.data.model.news.detail.NewsDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApi {

    @GET("/api/v1/newsletters/posts")
    suspend fun getNewsMainApi(): Response<ResponseBody<MainNewsResponse>>

    @GET("/api/v1/newsletter/posts/{postId}")
    suspend fun getDetailNewsApi(
        @Path("postId") postId : Int
    ): Response<ResponseBody<NewsDetailResponse>>

    @POST("/api/v1/newsletter/comment/{postId}")
    suspend fun postCommentNewsApi(
        @Path("postId") postId: Int,
        @Body request : NewsCommentRequest
    ): Response<ResponseBody<CommonResponse>>

    @POST("/api/v1/newsletter/posts/report/{postId}")
    suspend fun postReportNewsApi(
        @Path("postId") postId: Int,
        @Body request : ReportRequest
    ): Response<ResponseBody<CommonResponse>>

    @POST("/api/v1/newsletter/comment/report/{commentId}")
    suspend fun postReportCommentNewsApi(
        @Path("commentId") commentId: Int,
        @Body request : ReportRequest
    ): Response<ResponseBody<CommonResponse>>


    @DELETE("/api/v1/newsletter/comment/{commentId}")
    suspend fun deleteCommentNewsApi(
        @Path("commentId") commentId: Int,
    ): Response<ResponseBody<CommonResponse>>
}