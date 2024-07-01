package com.umc.ttoklip.data.repository.fcm

import com.umc.ttoklip.data.model.CommonResponse
import com.umc.ttoklip.data.model.fcm.FCMTokenRequest
import com.umc.ttoklip.module.NetworkResult

interface FCMRepository {
    suspend fun patchFCMToken(request : FCMTokenRequest) : NetworkResult<CommonResponse>
}