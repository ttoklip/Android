package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.town.CreateCommunicationsResponse
import com.umc.ttoklip.data.model.town.PatchCommunicationResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface WriteCommsApi {
    //소통해요 게시글 생성
    @POST("/api/v1/town/comms")
    suspend fun createCommunications(
        @Part("title") title: String,
        @Part("content") content: String,
        @Part("images") images: List<MultipartBody.Part>
    ): Response<ResponseBody<CreateCommunicationsResponse>>

    //소통해요 게시글 수정
    @PATCH("/api/v1/town/comms/{postId}")
    suspend fun patchCommunications(
        @Part("title") title: String,
        @Part("content") content: String,
        @Part("images") images: List<MultipartBody.Part>,
        @Path("postId") postId: Long
    ): Response<ResponseBody<PatchCommunicationResponse>>
}