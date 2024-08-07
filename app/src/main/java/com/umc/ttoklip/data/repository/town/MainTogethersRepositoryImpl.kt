package com.umc.ttoklip.data.repository.town

import com.umc.ttoklip.data.api.MainTogethersApi
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.town.TogethersResponse
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import javax.inject.Inject

class MainTogethersRepositoryImpl @Inject constructor(private val api: MainTogethersApi) :
    MainTogethersRepository {
    override suspend fun getTogethers(
        page: Int,
        startMoney: Long?,
        lastMoney: Long?,
        startParty: Long?,
        lastParty: Long?
    ): NetworkResult<TogethersResponse> {
        return handleApi({
            api.commsList(
                page,
                startMoney,
                lastMoney,
                startParty,
                lastParty
            )
        }) { response: ResponseBody<TogethersResponse> -> response.result }
    }

}