package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.home.HomeResponse
import retrofit2.Response
import retrofit2.http.GET

interface HomeApi {

    @GET("/api/v1/home")
    suspend fun getHomeMainApi(): Response<ResponseBody<HomeResponse>>

}