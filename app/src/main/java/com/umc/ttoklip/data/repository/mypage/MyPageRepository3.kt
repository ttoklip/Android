package com.umc.ttoklip.data.repository.mypage

import com.umc.ttoklip.data.model.mypage.MyBlockUserResponse
import com.umc.ttoklip.data.model.mypage.NoticeResponse
import com.umc.ttoklip.data.model.mypage.RestrictedResponse
import com.umc.ttoklip.module.NetworkResult

interface MyPageRepository3 {
    suspend fun getRestrictedReason(): NetworkResult<RestrictedResponse>
    suspend fun getBlockedUser(): NetworkResult<MyBlockUserResponse>
    suspend fun deleteBlockUser(userId: Long): NetworkResult<Unit>

    suspend fun getNotice(): NetworkResult<NoticeResponse>
}