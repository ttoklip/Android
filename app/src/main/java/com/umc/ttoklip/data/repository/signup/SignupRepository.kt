package com.umc.ttoklip.data.repository.signup

import com.umc.ttoklip.data.model.signup.SignupRequest
import com.umc.ttoklip.data.model.signup.SignupResponse
import com.umc.ttoklip.module.NetworkResult
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface SignupRepository {
    suspend fun checkNickname(nick:String): NetworkResult<SignupResponse>
    suspend fun savePrivacy(photo: MultipartBody.Part,
                            info:MutableMap<String, RequestBody>
                            ,cate:List<MultipartBody.Part>)
    : NetworkResult<SignupResponse>
}