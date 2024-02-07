package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.news.MainNewsResponse
import retrofit2.Response
import retrofit2.http.GET

interface NewsApi {

    @GET("/api/v1/newsletters/posts")
    suspend fun getNewsMainApi(): Response<ResponseBody<MainNewsResponse>>

}