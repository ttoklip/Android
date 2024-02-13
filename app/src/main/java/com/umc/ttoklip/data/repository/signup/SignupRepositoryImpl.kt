package com.umc.ttoklip.data.repository.signup

import com.umc.ttoklip.data.api.SignupApi
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.signup.SignupResponse
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import javax.inject.Inject

class SignupRepositoryImpl @Inject constructor(
    private val api: SignupApi
): SignupRepository {
    override suspend fun checkNickname(nick: String): NetworkResult<SignupResponse> {
        return handleApi({api.nickCheck(nick)}){response: ResponseBody<SignupResponse> ->response.result}
    }
}