package com.umc.ttoklip.data.repository.honeytip

import com.umc.ttoklip.data.model.honeytip.CreateHoneyTipResponse
import com.umc.ttoklip.data.model.honeytip.HoneyTipMainResponse
import com.umc.ttoklip.data.model.honeytip.InquireHoneyTipResponse
import com.umc.ttoklip.data.model.honeytip.InquireQuestionResponse
import com.umc.ttoklip.module.NetworkResult
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface HoneyTipRepository {
    suspend fun getHoneyTipMain(): NetworkResult<HoneyTipMainResponse>
    suspend fun createHoneyTip(
        title: RequestBody,
        content: RequestBody,
        category: RequestBody,
        images: Array<MultipartBody.Part>,
        url: RequestBody
    ): NetworkResult<CreateHoneyTipResponse>

    suspend fun inquireHoneyTip(honeyTipId: Int): NetworkResult<InquireHoneyTipResponse>
    suspend fun createQuestion(
        title: RequestBody,
        content: RequestBody,
        category: RequestBody,
        images: Array<MultipartBody.Part>
    ): NetworkResult<CreateHoneyTipResponse>

    suspend fun inquireQuestion(questionId: Int): NetworkResult<InquireQuestionResponse>
}