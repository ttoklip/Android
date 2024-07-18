package com.umc.ttoklip.data.repository.stranger

import com.umc.ttoklip.data.api.OtherApi
import com.umc.ttoklip.data.model.CommonResponse
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.mypage.MyHoneyTipsResponse
import com.umc.ttoklip.data.model.news.ReportRequest
import com.umc.ttoklip.data.model.stranger.OtherUserInfoResponse
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import javax.inject.Inject

class StrangerRepositoryImpl @Inject constructor(
    private val api : OtherApi
) : StrangerRepository {

    override suspend fun getStranger(nick: String): NetworkResult<OtherUserInfoResponse> {
        return handleApi({api.getStrangeInfo(nick)}) {response: ResponseBody<OtherUserInfoResponse> ->response.result}
    }

    override suspend fun getStrangerTip(page: Int, id: Int): NetworkResult<MyHoneyTipsResponse> {
        return handleApi({api.getStrangerTip(id,page)}) {response: ResponseBody<MyHoneyTipsResponse> -> response.result}
    }


    override suspend fun reportUser(nick: String, request: ReportRequest): NetworkResult<CommonResponse> {
        return handleApi({api.postReportUserApi(nick, request)}) {response: ResponseBody<CommonResponse> -> response.result}
    }
}