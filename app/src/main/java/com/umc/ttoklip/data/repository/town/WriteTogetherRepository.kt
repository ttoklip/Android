package com.umc.ttoklip.data.repository.town

import com.umc.ttoklip.data.model.CommonResponse
import com.umc.ttoklip.data.model.town.CreateTogethersRequest
import com.umc.ttoklip.data.model.town.CreateTogethersResponse
import com.umc.ttoklip.data.model.town.PatchTogetherResponse
import com.umc.ttoklip.module.NetworkResult
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part
import retrofit2.http.Path

interface WriteTogetherRepository {
    suspend fun createTogether(body: CreateTogethersRequest): NetworkResult<CommonResponse>
    suspend fun patchTogether(
        title: RequestBody,
        content: RequestBody,
        totalPrice: RequestBody,
        location: RequestBody,
        chatUrl: RequestBody,
        party: RequestBody,
        images: List<MultipartBody.Part?>,
        itemUrls: RequestBody,
        postId: Long
    ): NetworkResult<PatchTogetherResponse>
}