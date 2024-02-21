package com.umc.ttoklip.data.repository.mypage

import com.umc.ttoklip.data.model.mypage.MyCommunitiesResponse
import com.umc.ttoklip.data.model.mypage.MyHoneyTipsResponse
import com.umc.ttoklip.data.model.mypage.MyQuestionResponse
import com.umc.ttoklip.data.model.mypage.MyTogetherResponse
import com.umc.ttoklip.module.NetworkResult

interface MyPostRepository {
    suspend fun getMyQuestions(page: Int): NetworkResult<MyQuestionResponse>
    suspend fun getMyTogethers(page: Int): NetworkResult<MyTogetherResponse>
    suspend fun getMyHoneyTips(page: Int): NetworkResult<MyHoneyTipsResponse>
    suspend fun getMyCommunications(page: Int): NetworkResult<MyCommunitiesResponse>
}