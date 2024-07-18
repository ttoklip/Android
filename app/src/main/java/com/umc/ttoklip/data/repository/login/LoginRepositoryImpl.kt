package com.umc.ttoklip.data.repository.login

import com.umc.ttoklip.data.api.LoginApi
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.login.LoginLocalRequest
import com.umc.ttoklip.data.model.login.LoginRequest
import com.umc.ttoklip.data.model.login.LoginResponse
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val api: LoginApi
): LoginRepository {

    override suspend fun postLogin(request: LoginRequest): NetworkResult<LoginResponse> {
        return handleApi({api.postLogin(request)}){response:ResponseBody<LoginResponse>->response.result}
    }
    override suspend fun postLoginLocal(request: LoginLocalRequest): NetworkResult<LoginResponse> {
        return handleApi({api.postLocalLogin(request)}){response:ResponseBody<LoginResponse>->response.result}
    }
}