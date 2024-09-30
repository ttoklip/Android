package com.umc.ttoklip.data.repository.town

import com.umc.ttoklip.data.model.town.CommsResponse
import com.umc.ttoklip.module.NetworkResult
import retrofit2.http.Query

interface MainCommsRepository {
    suspend fun getComms(page :Int, criteria: String): NetworkResult<CommsResponse>
}