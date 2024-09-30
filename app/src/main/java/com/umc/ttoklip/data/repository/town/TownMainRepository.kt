package com.umc.ttoklip.data.repository.town

import com.umc.ttoklip.data.model.town.TownMainResponse
import com.umc.ttoklip.module.NetworkResult

interface TownMainRepository {
    suspend fun getTerm(): NetworkResult<TownMainResponse>
}