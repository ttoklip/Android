package com.umc.ttoklip.data.repository.town

import com.umc.ttoklip.data.api.WriteTogetherApi
import com.umc.ttoklip.data.model.CommonResponse
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.town.CreateTogethersRequest
import com.umc.ttoklip.data.model.town.CreateTogethersResponse
import com.umc.ttoklip.data.model.town.PatchTogetherResponse
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class WriteTogetherRepositoryImpl @Inject constructor(private val api: WriteTogetherApi) :
    WriteTogetherRepository {
    override suspend fun createTogether(body: CreateTogethersRequest): NetworkResult<CommonResponse> {
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
        }) { response: ResponseBody<CommonResponse> -> response.result }
    }

    override suspend fun patchTogether(
        title: RequestBody,
        content: RequestBody,
        totalPrice: RequestBody,
        location: RequestBody,
        chatUrl: RequestBody,
        party: RequestBody,
        images: List<MultipartBody.Part?>,
        itemUrls: RequestBody,
        postId: Long
    ): NetworkResult<PatchTogetherResponse> {
        return handleApi({
            api.patchTogethers(
                title,
                content,
                totalPrice,
                location,
                chatUrl,
                party,
                images,
                itemUrls,
                postId
            )
        }) {response: ResponseBody<PatchTogetherResponse> -> response.result}
    }
}