package com.umc.ttoklip.data.repository.signup

import com.umc.ttoklip.data.model.StandardResponse
import com.umc.ttoklip.data.model.signup.SignupRequest
import com.umc.ttoklip.data.model.signup.SignupResponse
import com.umc.ttoklip.data.model.signup.VerifyRequest
import com.umc.ttoklip.module.NetworkResult
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface SignupRepository {
    suspend fun verifySend(request:VerifyRequest):NetworkResult<StandardResponse>
    suspend fun verifyCheck(request: VerifyRequest):NetworkResult<StandardResponse>
    suspend fun checkId(id:String):NetworkResult<StandardResponse>
    suspend fun checkNickname(nick:String): NetworkResult<SignupResponse>
    suspend fun checkNicknameLocal(nick:String): NetworkResult<SignupResponse>
    suspend fun savePrivacy(photo: MultipartBody.Part,
                            info:MutableMap<String, RequestBody>
                            ,cate:List<MultipartBody.Part>)
    : NetworkResult<SignupResponse>
    suspend fun savePrivacyLocal(photo: MultipartBody.Part,
                                 info: MutableMap<String, RequestBody>,
                                 cate: List<MultipartBody.Part>)
    : NetworkResult<SignupResponse>
//    suspend fun savePrivacy(userInfo:SignupRequest)
//            : NetworkResult<SignupResponse>
}