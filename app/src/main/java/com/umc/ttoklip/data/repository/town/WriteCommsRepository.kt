package com.umc.ttoklip.data.repository.town

import com.umc.ttoklip.data.model.town.CreateCommunicationsRequest
import com.umc.ttoklip.data.model.town.CreateCommunicationsResponse
import com.umc.ttoklip.data.model.town.EditCommunication
import com.umc.ttoklip.data.model.town.PatchCommunicationResponse
import com.umc.ttoklip.module.NetworkResult
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface WriteCommsRepository {
    suspend fun createComms(body: CreateCommunicationsRequest): NetworkResult<CreateCommunicationsResponse>
    suspend fun patchComms(
        postId: Long,
        title: RequestBody,
        content: RequestBody,
        deleteImageIds: RequestBody,
        addImages: List<MultipartBody.Part?>,
        url: RequestBody
    ): NetworkResult<PatchCommunicationResponse>
}