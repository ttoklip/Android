package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.town.MemberStreetInfoResponse
import com.umc.ttoklip.data.model.town.TownMainResponse
import retrofit2.Response
import retrofit2.http.GET

interface TownMainApi {

    @GET("/api/v1/town/main")
    suspend fun getTerm(): Response<ResponseBody<TownMainResponse>>
}