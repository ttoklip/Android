package com.umc.ttoklip.data.repository.mypage

import com.umc.ttoklip.data.api.MyPage3Api
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.mypage.MyBlockUserResponse
import com.umc.ttoklip.data.model.mypage.NoticeResponse
import com.umc.ttoklip.data.model.mypage.RestrictedResponse
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import javax.inject.Inject

class MyPageRepository3Impl @Inject constructor(
    private val api: MyPage3Api
) : MyPageRepository3 {
    override suspend fun getRestrictedReason(): NetworkResult<RestrictedResponse> {
        return handleApi({ api.getRestrictedReason() }) { response: ResponseBody<RestrictedResponse> -> response.result }
    }

    override suspend fun getBlockedUser(): NetworkResult<MyBlockUserResponse> {
        return handleApi({ api.getMyBlockedUser() }) { response: ResponseBody<MyBlockUserResponse> -> response.result }
    }

    override suspend fun deleteBlockUser(userId: Long): NetworkResult<Unit> {
        return handleApi({ api.deleteBlockUser(userId) }) { response: ResponseBody<Unit> -> response.result }
    }

    override suspend fun getNotice(): NetworkResult<NoticeResponse> {
        return handleApi({api.getNotices()}){response:ResponseBody<NoticeResponse>->response.result}
    }

}