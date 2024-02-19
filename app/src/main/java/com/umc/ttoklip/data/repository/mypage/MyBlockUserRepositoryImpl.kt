package com.umc.ttoklip.data.repository.mypage

import com.umc.ttoklip.data.api.MyBlockUserApi
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.mypage.MyBlockUserResponse
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import javax.inject.Inject

class MyBlockUserRepositoryImpl @Inject constructor(private val api: MyBlockUserApi) :
    MyBlockUserRepository {
    override suspend fun getBlockedUser(): NetworkResult<MyBlockUserResponse> {
        return handleApi({ api.getMyBlockedUser() }) { response: ResponseBody<MyBlockUserResponse> -> response.result }
    }

    override suspend fun deleteBlockUser(userId: Long): NetworkResult<Unit> {
        return handleApi({ api.deleteBlockUser(userId) }) { response: ResponseBody<Unit> -> response.result }
    }
}