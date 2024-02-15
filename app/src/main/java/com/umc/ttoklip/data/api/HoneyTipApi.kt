package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.honeytip.CreateHoneyTipResponse
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.honeytip.HoneyTipMainResponse
import com.umc.ttoklip.data.model.honeytip.InquireHoneyTipResponse
import com.umc.ttoklip.data.model.honeytip.InquireQuestionResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface HoneyTipApi {
    @GET("/api/v1/common/main")
    suspend fun getHoneyTipMain(): Response<ResponseBody<HoneyTipMainResponse>>

    //꿀팁
    @Multipart
    @POST("/api/v1/honeytips/posts")
    suspend fun postNewHoneyTip(
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody,
        @Part("category") category: RequestBody,
        @Part images: Array<MultipartBody.Part>,
        @Part("url") url: RequestBody
    ): Response<ResponseBody<CreateHoneyTipResponse>>

    @GET("/api/v1/honeytips/posts/{postId}")
    suspend fun getHoneyTip(@Path("postId") honeyTipId: Int): Response<ResponseBody<InquireHoneyTipResponse>>

    //질문
    @Multipart
    @POST("/api/v1/question/post")
    suspend fun postNewQuestion(
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody,
        @Part("category") category: RequestBody,
        @Part images: Array<MultipartBody.Part>
    ): Response<ResponseBody<CreateHoneyTipResponse>>

    @GET("/api/v1/question/post/{postId}")
    suspend fun getQuestion(@Path("postId") questionId: Int): Response<ResponseBody<InquireQuestionResponse>>

}