package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.CommonResponse
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.fcm.FCMTokenRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH

interface FCMApi {
    @PATCH("/api/v1/notification/fcm_token")
    suspend fun patchFCMToken(
        @Body request : FCMTokenRequest
    ): Response<ResponseBody<CommonResponse>>
}