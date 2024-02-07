package com.umc.ttoklip.data.repository.honeytip

import android.net.Uri
import com.umc.ttoklip.data.api.HoneyTipApi
import com.umc.ttoklip.data.model.CreateHoneyTipRequest
import com.umc.ttoklip.data.model.CreateHoneyTipResponse
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class HoneyTipRepositoryImpl @Inject constructor(
    private val api: HoneyTipApi
): HoneyTipRepository {
    /*override suspend fun createHoneyTip(request: CreateHoneyTipRequest): NetworkResult<CreateHoneyTipResponse> {
        return handleApi({api.postNewHoneyTip(request)}) {response: ResponseBody<CreateHoneyTipResponse> -> response.result}
    }*/
    /*override suspend fun createHoneyTip(
        request: RequestBody,
        uri: Array<MultipartBody.Part>
    ): NetworkResult<CreateHoneyTipResponse> {
        return handleApi({api.postNewHoneyTip(request, uri)}) {response: ResponseBody<CreateHoneyTipResponse> -> response.result}
    }*/

    override suspend fun createHoneyTip(
        title: RequestBody,
        content: RequestBody,
        category: RequestBody,
        uri: Array<MultipartBody.Part>
    ): NetworkResult<CreateHoneyTipResponse> {
        return handleApi({api.postNewHoneyTip(title, content, category, uri)}) {response: ResponseBody<CreateHoneyTipResponse> -> response.result}
    }

}