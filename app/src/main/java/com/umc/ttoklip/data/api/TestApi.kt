package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.TestResponse
import retrofit2.Response
import retrofit2.http.GET

//테스트용 api
interface TestApi {
    @GET("/")
    suspend fun testGet(): Response<TestResponse>
}