package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.town.CreateCommunicationsRequest
import com.umc.ttoklip.data.model.town.CreateCommunicationsResponse
import com.umc.ttoklip.data.model.town.PatchCommunicationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface WriteCommsApi {
    //소통해요 게시글 생성
    @POST("/api/v1/town/comms")
    suspend fun createCommunications(
        @Body body: CreateCommunicationsRequest
    ): Response<ResponseBody<CreateCommunicationsResponse>>

    //소통해요 게시글 수정
    @PATCH("/api/v1/town/comms/{postId}")
    suspend fun patchCommunications(
        @Body body: CreateCommunicationsRequest,
        @Path("postId") postId: Long
    ): Response<ResponseBody<PatchCommunicationResponse>>
}