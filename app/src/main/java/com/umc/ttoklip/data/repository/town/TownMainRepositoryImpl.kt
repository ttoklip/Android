package com.umc.ttoklip.data.repository.town

import com.umc.ttoklip.data.api.TownMainApi
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.town.TownMainResponse
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import javax.inject.Inject

class TownMainRepositoryImpl @Inject constructor(
    private val api: TownMainApi
): TownMainRepository {
    override suspend fun getTerm(): NetworkResult<TownMainResponse> {
        return handleApi({api.getTerm()}) {response: ResponseBody<TownMainResponse> -> response.result}
    }

}