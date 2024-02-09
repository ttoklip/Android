package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.CommonResponse
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.news.MainNewsResponse
import com.umc.ttoklip.data.model.news.comment.NewsCommentRequest
import com.umc.ttoklip.data.model.news.detail.NewsDetailResponse
import retrofit2.Response
import retrofit2.http.Body
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

}