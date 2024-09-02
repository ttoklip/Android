package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.town.TogethersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MainTogethersApi {
    @GET("/api/v1/town/main/cart")
    suspend fun commsList(
        @Query("page") page:Int,
        @Query("startMoney") startMoney: Long?,
        @Query("lastMoney") lastMoney: Long?,
        @Query("startParty") startParty: Long?,
        @Query("lastParty") lastParty: Long?
    ): Response<ResponseBody<TogethersResponse>>
}