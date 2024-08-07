package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.signup.TermResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TermApi {
    @GET("/api/v1/auth/agree")
    suspend fun getTerm()
            : Response<ResponseBody<TermResponse>>
}