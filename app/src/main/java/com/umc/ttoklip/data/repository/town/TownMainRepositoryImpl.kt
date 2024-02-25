package com.umc.ttoklip.data.repository.town

import com.umc.ttoklip.data.api.TownMainApi
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.town.townMainResponse
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import javax.inject.Inject

class TownMainRepositoryImpl @Inject constructor(
    private val api: TownMainApi
): TownMainRepository {
    override suspend fun getTerm(): NetworkResult<townMainResponse> {
        return handleApi({api.getTerm()}) {response: ResponseBody<townMainResponse> -> response.result}
    }

}