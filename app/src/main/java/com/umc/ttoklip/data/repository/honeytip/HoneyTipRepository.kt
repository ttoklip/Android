package com.umc.ttoklip.data.repository.honeytip

import android.net.Uri
import com.umc.ttoklip.data.model.CreateHoneyTipRequest
import com.umc.ttoklip.data.model.CreateHoneyTipResponse
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.module.NetworkResult
import okhttp3.MultipartBody

interface HoneyTipRepository {
    suspend fun createHoneyTip(request: CreateHoneyTipRequest, uri: List<MultipartBody.Part>?): NetworkResult<CreateHoneyTipResponse>
}