package com.umc.ttoklip.data.repository.town

import com.umc.ttoklip.data.api.MainCommsApi
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.town.CommsResponse
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import javax.inject.Inject

class MainCommsRepositoryImpl @Inject constructor(private val api: MainCommsApi) :
    MainCommsRepository {
    override suspend fun getComms(): NetworkResult<CommsResponse> {
        return handleApi({ api.commsList() }) { response: ResponseBody<CommsResponse> -> response.result }
    }
}