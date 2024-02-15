package com.umc.ttoklip.data.repository.honeytip

import com.umc.ttoklip.data.model.honeytip.CreateHoneyTipResponse
import com.umc.ttoklip.data.model.honeytip.HoneyTipMainResponse
import com.umc.ttoklip.data.model.honeytip.InquireHoneyTipResponse
import com.umc.ttoklip.data.model.honeytip.InquireQuestionResponse
import com.umc.ttoklip.data.model.honeytip.request.ReportRequest
import com.umc.ttoklip.module.NetworkResult
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body

interface HoneyTipRepository {
    //꿀팁
    suspend fun getHoneyTipMain(): NetworkResult<HoneyTipMainResponse>
    suspend fun createHoneyTip(
        title: RequestBody,
        content: RequestBody,
        category: RequestBody,
        images: Array<MultipartBody.Part>,
        url: RequestBody
    ): NetworkResult<CreateHoneyTipResponse>

    suspend fun inquireHoneyTip(honeyTipId: Int): NetworkResult<InquireHoneyTipResponse>

    suspend fun deleteHoneyTip(honeyTipId: Int): NetworkResult<CreateHoneyTipResponse>

    suspend fun editHoneyTip(honeyTipId: Int): NetworkResult<CreateHoneyTipResponse>

    suspend fun reportHoneyTip(honeyTipId: Int, request: ReportRequest): NetworkResult<CreateHoneyTipResponse>

    //질문
    suspend fun createQuestion(
        title: RequestBody,
        content: RequestBody,
        category: RequestBody,
        images: Array<MultipartBody.Part>
    ): NetworkResult<CreateHoneyTipResponse>

    suspend fun inquireQuestion(questionId: Int): NetworkResult<InquireQuestionResponse>

    suspend fun reportQuestion(questionId: Int, request: ReportRequest): NetworkResult<CreateHoneyTipResponse>

}