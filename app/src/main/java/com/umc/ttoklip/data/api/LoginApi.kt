package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.login.LoginResponse
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.login.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginApi {
    @POST("/api/v1/auth")
    suspend fun postLogin(@Body request: LoginRequest)
    : Response<ResponseBody<LoginResponse>>
}