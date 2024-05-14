package com.umc.ttoklip.data.repository.mypage

import com.umc.ttoklip.data.api.MyAccountManageUsageApi
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.mypage.MyBlockUserResponse
import com.umc.ttoklip.data.model.mypage.RestrictedResponse
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import javax.inject.Inject

class MyAccountManageUsageRepositoryImpl @Inject constructor(
    private val api: MyAccountManageUsageApi
) : MyAccountManageUsageRepository {
    override suspend fun getRestrictedReason(): NetworkResult<RestrictedResponse> {
        return handleApi({ api.getRestrictedReason() }) { response: ResponseBody<RestrictedResponse> -> response.result }
    }

    override suspend fun getBlockedUser(): NetworkResult<MyBlockUserResponse> {
        return handleApi({ api.getMyBlockedUser() }) { response: ResponseBody<MyBlockUserResponse> -> response.result }
    }

    override suspend fun deleteBlockUser(userId: Long): NetworkResult<Unit> {
        return handleApi({ api.deleteBlockUser(userId) }) { response: ResponseBody<Unit> -> response.result }
    }

}