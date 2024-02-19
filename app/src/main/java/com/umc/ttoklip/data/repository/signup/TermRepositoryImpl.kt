package com.umc.ttoklip.data.repository.signup

import com.umc.ttoklip.data.api.TermApi
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.signup.SignupResponse
import com.umc.ttoklip.data.model.signup.TermResponse
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import javax.inject.Inject

class TermRepositoryImpl @Inject constructor(
    private val api: TermApi
): TermRepository {
    override suspend fun getTerm(page: Int): NetworkResult<TermResponse> {
        return handleApi({api.getTerm(page)}) {response: ResponseBody<TermResponse> ->response.result}
    }
}