package com.umc.ttoklip.data.repository.town

import com.umc.ttoklip.data.model.town.CreateCommunicationsRequest
import com.umc.ttoklip.data.model.town.CreateCommunicationsResponse
import com.umc.ttoklip.data.model.town.PatchCommunicationResponse
import com.umc.ttoklip.module.NetworkResult

interface WriteCommsRepository {
    suspend fun createComms(body: CreateCommunicationsRequest): NetworkResult<CreateCommunicationsResponse>
    suspend fun patchComms(
        postId: Long,
        body: CreateCommunicationsRequest
    ): NetworkResult<PatchCommunicationResponse>
}