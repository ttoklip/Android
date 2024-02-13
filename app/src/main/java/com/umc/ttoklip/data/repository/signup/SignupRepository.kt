package com.umc.ttoklip.data.repository.signup

import com.umc.ttoklip.data.model.login.LoginRequest
import com.umc.ttoklip.data.model.login.LoginResponse
import com.umc.ttoklip.data.model.signup.SignupResponse
import com.umc.ttoklip.module.NetworkResult

interface SignupRepository {
    suspend fun checkNickname(nick:String): NetworkResult<SignupResponse>
}