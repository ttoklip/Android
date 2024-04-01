package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.mypage.MyCommunitiesResponse
import com.umc.ttoklip.data.model.mypage.MyHoneyTipsResponse
import com.umc.ttoklip.data.model.mypage.MyQuestionResponse
import com.umc.ttoklip.data.model.mypage.MyTogetherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MyPostApi {
    @GET("/api/v1/my-page/question")
    suspend fun getMyQuestions(@Query("page") page: Int): Response<ResponseBody<MyQuestionResponse>>

    @GET("/api/v1/my-page/participate-deals")
    suspend fun getMyTogethers(@Query("page") page: Int): Response<ResponseBody<MyTogetherResponse>>

    @GET("/api/v1/stranger/participate-deals/{userId}")
    suspend fun getStrangerDeals(
        @Query("page") page: Int,
        @Path("userId") userId: Int
    ): Response<ResponseBody<MyTogetherResponse>>


    @GET("/api/v1/my-page/honeytip")
    suspend fun getMyHoneyTips(@Query("page") page: Int): Response<ResponseBody<MyHoneyTipsResponse>>

    @GET("/api/v1/my-page/community")
    suspend fun getMyCommunications(@Query("page") page: Int): Response<ResponseBody<MyCommunitiesResponse>>

}