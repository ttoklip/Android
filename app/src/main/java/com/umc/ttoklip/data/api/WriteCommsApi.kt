package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.honeytip.CreateHoneyTipResponse
import com.umc.ttoklip.data.model.town.CreateCommunicationsResponse
import com.umc.ttoklip.data.model.town.EditCommunication
import com.umc.ttoklip.data.model.town.PatchCommunicationResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface WriteCommsApi {
    //소통해요 게시글 생성
    @Multipart
    @POST("/api/v1/town/comms")
    suspend fun createCommunications(
        @Part("title") title: String,
        @Part("content") content: String,
        @Part images: List<MultipartBody.Part?>
    ): Response<ResponseBody<CreateCommunicationsResponse>>

    //소통해요 게시글 수정
    @Multipart
    @PATCH("/api/v1/town/comms/{postId}")
    suspend fun patchCommunications(
        @Path("postId") postId: Long,
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody,
        @Part("deleteImageIds") deleteImageIds: RequestBody,
        @Part addImages: List<MultipartBody.Part?>,
        @Part("url") url: RequestBody
    ): Response<ResponseBody<PatchCommunicationResponse>>
}