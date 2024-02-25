package com.umc.ttoklip.data.repository.town

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.town.townMainResponse
import com.umc.ttoklip.module.NetworkResult
import retrofit2.Response

interface TownMainRepository {
    suspend fun getTerm(): NetworkResult<townMainResponse>
}