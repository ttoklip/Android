package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.CreateHoneyTipRequest
import com.umc.ttoklip.data.model.CreateHoneyTipResponse
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.TestResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface HoneyTipApi {
    @GET("/")
    suspend fun testGet(): Response<TestResponse>

    @POST("/api/v1/honeytips/posts")
    suspend fun postNewHoneyTip(@Body request: CreateHoneyTipRequest): Response<ResponseBody<CreateHoneyTipResponse>>
}