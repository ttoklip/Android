package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.mypage.MyCommunitiesResponse
import com.umc.ttoklip.data.model.mypage.MyHoneyTipsResponse
import com.umc.ttoklip.data.model.mypage.MyQuestionResponse
import com.umc.ttoklip.data.model.mypage.MyTogetherResponse
import retrofit2.Response
import retrofit2.http.GET

interface MyPostApi {
    @GET("/api/v1/my-page/question")
    suspend fun getMyQuestions(): Response<ResponseBody<MyQuestionResponse>>

    @GET("/api/v1/my-page/participate-deals")
    suspend fun getMyTogethers(): Response<ResponseBody<MyTogetherResponse>>

    @GET("/api/v1/my-page/honeytip")
    suspend fun getMyHoneyTips(): Response<ResponseBody<MyHoneyTipsResponse>>

    @GET("/api/v1/my-page/community")
    suspend fun getMyCommunications(): Response<ResponseBody<MyCommunitiesResponse>>

}