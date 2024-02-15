package com.umc.ttoklip.data.repository.town

import com.umc.ttoklip.data.api.WriteTogetherApi
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.town.CreateTogethersRequest
import com.umc.ttoklip.data.model.town.CreateTogethersResponse
import com.umc.ttoklip.data.model.town.PatchTogetherResponse
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import javax.inject.Inject

class WriteTogetherRepositoryImpl @Inject constructor(private val api: WriteTogetherApi) :
    WriteTogetherRepository {
    override suspend fun createTogether(body: CreateTogethersRequest): NetworkResult<CreateTogethersResponse> {
        return handleApi({
            api.createTogethers(
                title = body.title,
                content = body.content,
                totalPrice = body.totalPrice,
                location = body.location,
                chatUrl = body.chatUrl,
                party = body.party,
                images = body.images,
                itemUrls = body.itemUrls
            )
        }) { response: ResponseBody<CreateTogethersResponse> -> response.result }
    }

    override suspend fun patchTogether(
        postId: Long,
        body: CreateTogethersRequest
    ): NetworkResult<PatchTogetherResponse> {
        return handleApi({
            api.patchTogethers(
                postId = postId,
                title = body.title,
                content = body.content,
                totalPrice = body.totalPrice,
                location = body.location,
                chatUrl = body.chatUrl,
                party = body.party,
                images = body.images,
                itemUrls = body.itemUrls
            )
        }) { response: ResponseBody<PatchTogetherResponse> -> response.result }
    }
}