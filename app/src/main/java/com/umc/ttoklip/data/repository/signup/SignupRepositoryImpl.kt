package com.umc.ttoklip.data.repository.signup

import com.umc.ttoklip.data.api.SignupApi
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.StandardResponse
import com.umc.ttoklip.data.model.signup.SignupRequest
import com.umc.ttoklip.data.model.signup.SignupResponse
import com.umc.ttoklip.data.model.signup.VerifyRequest
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part
import javax.inject.Inject

class SignupRepositoryImpl @Inject constructor(
    private val api: SignupApi
): SignupRepository {
    override suspend fun verifySend(request: VerifyRequest): NetworkResult<StandardResponse> {
        return handleApi({api.verifySend(request)}){response:ResponseBody<StandardResponse>->response.result}
    }
    override suspend fun verifyCheck(request: VerifyRequest): NetworkResult<StandardResponse> {
        return handleApi({api.verifyCheck(request)}){response:ResponseBody<StandardResponse>->response.result}
    }
    override suspend fun checkId(id: String): NetworkResult<StandardResponse> {
        return handleApi({api.idCheck(id)}){response:ResponseBody<StandardResponse>->response.result}
    }

    override suspend fun checkNickname(nick: String): NetworkResult<SignupResponse> {
        return handleApi({api.nickCheck(nick)}) {response: ResponseBody<SignupResponse> ->response.result}
    }
    override suspend fun checkNicknameLocal(nick: String): NetworkResult<SignupResponse> {
        return handleApi({api.nickCheckLocal(nick)}) {response: ResponseBody<SignupResponse> ->response.result}
    }

    override suspend fun savePrivacy(photo:MultipartBody.Part,info:MutableMap<String, RequestBody>,cate:List<MultipartBody.Part>): NetworkResult<SignupResponse> {
        return handleApi({api.savePrivacy(photo,cate,info)}) {response: ResponseBody<SignupResponse> ->response.result}
    }
    override suspend fun savePrivacyLocal(photo:MultipartBody.Part,info:MutableMap<String, RequestBody>,cate:List<MultipartBody.Part>): NetworkResult<SignupResponse> {
        return handleApi({api.savePrivacyLocal(photo,cate,info)}) {response: ResponseBody<SignupResponse> ->response.result}
    }
}