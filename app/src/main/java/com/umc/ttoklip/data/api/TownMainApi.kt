package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.signup.TermResponse
import com.umc.ttoklip.data.model.town.townMainResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TownMainApi {

    @GET("/api/v1/town/main")
    suspend fun getTerm(): Response<ResponseBody<townMainResponse>>
}