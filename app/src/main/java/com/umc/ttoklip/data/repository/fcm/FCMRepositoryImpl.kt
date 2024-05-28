package com.umc.ttoklip.data.repository.fcm

import com.umc.ttoklip.data.api.FCMApi
import com.umc.ttoklip.data.model.CommonResponse
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.fcm.FCMTokenRequest
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import javax.inject.Inject

class FCMRepositoryImpl @Inject constructor(
    private val api : FCMApi
) : FCMRepository {

    override suspend fun patchFCMToken(request : FCMTokenRequest): NetworkResult<CommonResponse> {
        return handleApi({api.patchFCMToken(request)}) {response: ResponseBody<CommonResponse> -> response.result}
    }
}