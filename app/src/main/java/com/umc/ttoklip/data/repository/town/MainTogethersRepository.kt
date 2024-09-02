package com.umc.ttoklip.data.repository.town

import com.umc.ttoklip.data.model.town.TogethersResponse
import com.umc.ttoklip.module.NetworkResult

interface MainTogethersRepository {
    suspend fun getTogethers(
        page: Int,
        startMoney: Long?,
        lastMoney: Long?,
        startParty: Long?,
        lastParty: Long?
    ): NetworkResult<TogethersResponse>
}