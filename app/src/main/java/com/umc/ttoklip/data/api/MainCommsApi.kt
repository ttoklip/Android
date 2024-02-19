package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.town.CommsResponse
import retrofit2.Response
import retrofit2.http.GET

interface MainCommsApi {
    @GET("/api/v1/town/main/community")
    suspend fun commsList(): Response<ResponseBody<CommsResponse>>
}