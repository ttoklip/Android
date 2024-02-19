package com.umc.ttoklip.data.repository.mypage

import com.umc.ttoklip.data.api.MyPostApi
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.mypage.MyCommunitiesResponse
import com.umc.ttoklip.data.model.mypage.MyHoneyTipsResponse
import com.umc.ttoklip.data.model.mypage.MyQuestionResponse
import com.umc.ttoklip.data.model.mypage.MyTogetherResponse
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import javax.inject.Inject

class MyPostRepositoryImpl @Inject constructor(private val api: MyPostApi) : MyPostRepository {
    override suspend fun getMyQuestions(page: Int): NetworkResult<MyQuestionResponse> {
        return handleApi({ api.getMyQuestions(page) }) { response: ResponseBody<MyQuestionResponse> -> response.result }
    }

    override suspend fun getMyTogethers(page: Int): NetworkResult<MyTogetherResponse> {
        return handleApi({ api.getMyTogethers(page) }) { response: ResponseBody<MyTogetherResponse> -> response.result }
    }

    override suspend fun getMyHoneyTips(page: Int): NetworkResult<MyHoneyTipsResponse> {
        return handleApi({ api.getMyHoneyTips(page) }) { response: ResponseBody<MyHoneyTipsResponse> -> response.result }
    }

    override suspend fun getMyCommunications(page: Int): NetworkResult<MyCommunitiesResponse> {
        return handleApi({ api.getMyCommunications(page) }) { response: ResponseBody<MyCommunitiesResponse> -> response.result }
    }
}