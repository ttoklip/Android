package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.signup.SignupResponse
import com.umc.ttoklip.data.model.signup.SignupRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SignupApi {
    @GET("/api/v1/privacy/check-nickname")
    suspend fun nickCheck(@Query("nickname") nickname:String)
            : Response<ResponseBody<SignupResponse>>

    @POST("/api/v1/privacy/insert")
    suspend fun savePrivacy(@Body user:SignupRequest)
            :Response<ResponseBody<SignupResponse>>
}