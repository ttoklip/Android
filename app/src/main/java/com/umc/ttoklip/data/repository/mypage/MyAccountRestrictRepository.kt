package com.umc.ttoklip.data.repository.mypage

import com.umc.ttoklip.data.model.mypage.RestrictedResponse
import com.umc.ttoklip.module.NetworkResult

interface MyAccountRestrictRepository {
    suspend fun getRestrictedReason(): NetworkResult<RestrictedResponse>
}