package com.umc.ttoklip.data.repository.login

import com.umc.ttoklip.data.model.login.LoginRequest
import com.umc.ttoklip.data.model.login.LoginResponse
import com.umc.ttoklip.module.NetworkResult

interface LoginRepository {
    suspend fun postLogin(request: LoginRequest): NetworkResult<LoginResponse>
}