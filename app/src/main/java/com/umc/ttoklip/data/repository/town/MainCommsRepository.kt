package com.umc.ttoklip.data.repository.town

import com.umc.ttoklip.data.model.town.CommsResponse
import com.umc.ttoklip.module.NetworkResult

interface MainCommsRepository {
    suspend fun getComms(): NetworkResult<CommsResponse>
}