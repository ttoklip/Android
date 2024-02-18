package com.umc.ttoklip.data.repository.signup

import com.umc.ttoklip.data.api.SignupApi
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.signup.SignupRequest
import com.umc.ttoklip.data.model.signup.SignupResponse
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part
import javax.inject.Inject

class SignupRepositoryImpl @Inject constructor(
    private val api: SignupApi
): SignupRepository {
    override suspend fun checkNickname(nick: String): NetworkResult<SignupResponse> {
        return handleApi({api.nickCheck(nick)}) {response: ResponseBody<SignupResponse> ->response.result}
    }

    override suspend fun savePrivacy(photo:MultipartBody.Part,info:MutableMap<String, RequestBody>,cate:List<MultipartBody.Part>): NetworkResult<SignupResponse> {
        return handleApi({api.savePrivacy(photo,cate,info)}) {response: ResponseBody<SignupResponse> ->response.result}
    }

//    override suspend fun savePrivacy(userInfo:SignupRequest): NetworkResult<SignupResponse> {
//        return handleApi({api.savePrivacy(userInfo)}) {response: ResponseBody<SignupResponse> ->response.result}
//    }
}