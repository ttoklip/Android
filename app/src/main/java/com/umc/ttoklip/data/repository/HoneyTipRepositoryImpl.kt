package com.umc.ttoklip.data.repository

import com.umc.ttoklip.data.api.HoneyTipApi
import com.umc.ttoklip.data.model.CreateHoneyTipRequest
import com.umc.ttoklip.data.model.CreateHoneyTipResponse
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import javax.inject.Inject

class HoneyTipRepositoryImpl @Inject constructor(
    private val api: HoneyTipApi
): HoneyTipRepository {
    override suspend fun createHoneyTip(request: CreateHoneyTipRequest): NetworkResult<CreateHoneyTipResponse> {
        return handleApi({api.postNewHoneyTip(request)}) {response: ResponseBody<CreateHoneyTipResponse> -> response.result}
    }

}