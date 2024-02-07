package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.CreateHoneyTipRequest
import com.umc.ttoklip.data.model.CreateHoneyTipResponse
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.TestResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
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
    @POST("/api/v1/question/post")
    suspend fun postNewHoneyTip(
        //@Part("CreateHoneyTipRequest") request: RequestBody,
        @Part ("title") title: RequestBody,
        @Part ("content") content: RequestBody,
        @Part ("category") category: RequestBody,
        @Part images: Array<MultipartBody.Part>
    ): Response<ResponseBody<CreateHoneyTipResponse>>

    /*@GET("/api/v1/common/main")
    suspend fun getHoneyTipMain():*/
}