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
    override suspend fun getBMyQuestions(): NetworkResult<MyQuestionResponse> {
        return handleApi({ api.getMyQuestions() }) { response: ResponseBody<MyQuestionResponse> -> response.result }
    }

    override suspend fun getMyTogethers(): NetworkResult<MyTogetherResponse> {
        return handleApi({ api.getMyTogethers() }) { response: ResponseBody<MyTogetherResponse> -> response.result }
    }

    override suspend fun getMyHoneyTips(): NetworkResult<MyHoneyTipsResponse> {
        return handleApi({ api.getMyHoneyTips() }) { response: ResponseBody<MyHoneyTipsResponse> -> response.result }
    }

    override suspend fun getMyCommunications(): NetworkResult<MyCommunitiesResponse> {
        return handleApi({ api.getMyCommunications() }) { response: ResponseBody<MyCommunitiesResponse> -> response.result }
    }
}