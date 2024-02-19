package com.umc.ttoklip.data.repository.mypage

import com.umc.ttoklip.data.model.mypage.MyCommunitiesResponse
import com.umc.ttoklip.data.model.mypage.MyHoneyTipsResponse
import com.umc.ttoklip.data.model.mypage.MyQuestionResponse
import com.umc.ttoklip.data.model.mypage.MyTogetherResponse
import com.umc.ttoklip.module.NetworkResult

interface MyPostRepository {
    suspend fun getBMyQuestions(): NetworkResult<MyQuestionResponse>
    suspend fun getMyTogethers(): NetworkResult<MyTogetherResponse>
    suspend fun getMyHoneyTips(): NetworkResult<MyHoneyTipsResponse>
    suspend fun getMyCommunications(): NetworkResult<MyCommunitiesResponse>
}