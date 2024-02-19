package com.umc.ttoklip.data.repository.town

import com.umc.ttoklip.data.api.WriteCommsApi
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.town.CreateCommunicationsRequest
import com.umc.ttoklip.data.model.town.CreateCommunicationsResponse
import com.umc.ttoklip.data.model.town.PatchCommunicationResponse
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import javax.inject.Inject

class WriteCommsRepositoryImpl @Inject constructor(
    private val api: WriteCommsApi
) : WriteCommsRepository {
    override suspend fun createComms(body: CreateCommunicationsRequest): NetworkResult<CreateCommunicationsResponse> {
        return handleApi({
            api.createCommunications(
                content = body.content,
                title = body.title,
                images = body.images
            )
        }) { response: ResponseBody<CreateCommunicationsResponse> -> response.result }
    }

    override suspend fun patchComms(
        postId: Long,
        body: CreateCommunicationsRequest
    ): NetworkResult<PatchCommunicationResponse> {
        return handleApi({
            api.patchCommunications(
                postId = postId,
                content = body.content,
                title = body.title,
                images = body.images
            )
        }) { response: ResponseBody<PatchCommunicationResponse> -> response.result }
    }
}