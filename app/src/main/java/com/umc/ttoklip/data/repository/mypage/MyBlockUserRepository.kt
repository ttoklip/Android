package com.umc.ttoklip.data.repository.mypage

import com.umc.ttoklip.data.model.mypage.MyBlockUserResponse
import com.umc.ttoklip.module.NetworkResult

interface MyBlockUserRepository {
    suspend fun getBlockedUser(): NetworkResult<MyBlockUserResponse>
    suspend fun deleteBlockUser(userId: Long): NetworkResult<Unit>
}