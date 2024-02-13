package com.umc.ttoklip.data.repository.honeytip

import com.umc.ttoklip.data.api.HoneyTipApi
import com.umc.ttoklip.data.model.honeytip.CreateHoneyTipResponse
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.honeytip.HoneyTipMainResponse
import com.umc.ttoklip.data.model.honeytip.InquireHoneyTipResponse
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class HoneyTipRepositoryImpl @Inject constructor(
    private val api: HoneyTipApi
): HoneyTipRepository {
    override suspend fun createHoneyTip(
        title: RequestBody,
        content: RequestBody,
        category: RequestBody,
        images: Array<MultipartBody.Part>,
        uri: RequestBody
    ): NetworkResult<CreateHoneyTipResponse> {
        return handleApi({api.postNewHoneyTip(title, content, category, images, uri)}) {response: ResponseBody<CreateHoneyTipResponse> -> response.result}
    }

    override suspend fun createQuestion(
        title: RequestBody,
        content: RequestBody,
        category: RequestBody,
        images: Array<MultipartBody.Part>
    ): NetworkResult<CreateHoneyTipResponse> {
        return handleApi({api.postNewQuestion(title, content, category, images)}) {response: ResponseBody<CreateHoneyTipResponse> -> response.result}
    }

    override suspend fun getHoneyTipMain(): NetworkResult<HoneyTipMainResponse> {
        return handleApi({api.getHoneyTipMain()}) {response: ResponseBody<HoneyTipMainResponse> -> response.result}
    }

    override suspend fun inquireHoneyTip(honeyTipId: Int): NetworkResult<InquireHoneyTipResponse> {
        return handleApi({api.getHoneyTip(honeyTipId)}) {response: ResponseBody<InquireHoneyTipResponse> -> response.result}
    }

}