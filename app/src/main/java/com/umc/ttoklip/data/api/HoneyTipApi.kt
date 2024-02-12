package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.CreateHoneyTipResponse
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.honeytip.HoneyTipMainResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part

interface HoneyTipApi {
    /*@Multipart
    @POST("/api/v1/question/post")
    suspend fun postNewHoneyTip(
        @Part("CreateHoneyTipRequest") request: RequestBody,
        @Part images: List<MultipartBody.Part>
    ): Response<ResponseBody<CreateHoneyTipResponse>>*/

    @Multipart
    @POST("/api/v1/honeytips/posts")
    suspend fun postNewHoneyTip(
        @Part ("title") title: RequestBody,
        @Part ("content") content: RequestBody,
        @Part ("category") category: RequestBody,
        @Part images: Array<MultipartBody.Part>,
        @Part ("uri") uri: RequestBody
    ): Response<ResponseBody<CreateHoneyTipResponse>>

    @Multipart
    @POST("/api/v1/question/post")
    suspend fun postNewQuestion(
        @Part ("title") title: RequestBody,
        @Part ("content") content: RequestBody,
        @Part ("category") category: RequestBody,
        @Part images: Array<MultipartBody.Part>
    ): Response<ResponseBody<CreateHoneyTipResponse>>


    @GET("/api/v1/common/main")
    suspend fun getHoneyTipMain(): Response<ResponseBody<HoneyTipMainResponse>>
    /*@GET("/api/v1/common/main")
    suspend fun getHoneyTipMain():*/
}