package com.umc.ttoklip.data.repository

import com.umc.ttoklip.data.model.CreateHoneyTipRequest
import com.umc.ttoklip.data.model.CreateHoneyTipResponse
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.module.NetworkResult

interface HoneyTipRepository {
    suspend fun createHoneyTip(request: CreateHoneyTipRequest): NetworkResult<CreateHoneyTipResponse>
}