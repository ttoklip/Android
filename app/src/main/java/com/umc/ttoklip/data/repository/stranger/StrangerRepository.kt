package com.umc.ttoklip.data.repository.stranger

import com.umc.ttoklip.data.model.CommonResponse
import com.umc.ttoklip.data.model.mypage.MyHoneyTipsResponse
import com.umc.ttoklip.data.model.news.ReportRequest
import com.umc.ttoklip.data.model.signup.SignupResponse
import com.umc.ttoklip.data.model.stranger.OtherUserInfoResponse
import com.umc.ttoklip.data.model.stranger.StrangerResponse
import com.umc.ttoklip.module.NetworkResult

interface StrangerRepository {
    suspend fun getStranger(nick:String): NetworkResult<OtherUserInfoResponse>

    suspend fun getStrangerTip(page : Int, id : Int) : NetworkResult<MyHoneyTipsResponse>

    suspend fun reportUser(nick: String, request: ReportRequest): NetworkResult<CommonResponse>
}