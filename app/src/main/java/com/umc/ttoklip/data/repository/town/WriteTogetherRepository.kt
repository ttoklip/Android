package com.umc.ttoklip.data.repository.town

import com.umc.ttoklip.data.model.CommonResponse
import com.umc.ttoklip.data.model.town.CreateTogethersRequest
import com.umc.ttoklip.data.model.town.CreateTogethersResponse
import com.umc.ttoklip.data.model.town.PatchTogetherResponse
import com.umc.ttoklip.module.NetworkResult

interface WriteTogetherRepository {
    suspend fun createTogether(body: CreateTogethersRequest): NetworkResult<CommonResponse>
    suspend fun patchTogether(
        postId: Long,
        body: CreateTogethersRequest
    ): NetworkResult<PatchTogetherResponse>
}