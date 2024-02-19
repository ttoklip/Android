package com.umc.ttoklip.data.repository.signup

import com.umc.ttoklip.data.model.signup.TermResponse
import com.umc.ttoklip.module.NetworkResult

interface TermRepository {
    suspend fun getTerm(page:Int): NetworkResult<TermResponse>
}