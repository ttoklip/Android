package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.stranger.StrangerResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface StrangerApi {
    @POST("/api/v1/stranger")
    suspend fun getStranger(@Query("nickname") nick: String)
            : Response<ResponseBody<StrangerResponse>>
}