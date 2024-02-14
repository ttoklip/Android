package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.town.CreateTogethersRequest
import com.umc.ttoklip.data.model.town.CreateTogethersResponse
import com.umc.ttoklip.data.model.town.PatchTogetherResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface WriteTogetherApi {
    //함꼐해요 게시글 생성
    @POST("/api/v1/town/carts")
    suspend fun createTogethers(
        @Body body: CreateTogethersRequest
    ): Response<ResponseBody<CreateTogethersResponse>>

    //함께해요 게시글 수정
    @PATCH("/api/v1/town/carts/{postId}")
    suspend fun patchTogethers(
        @Body body: CreateTogethersRequest,
        @Path("postId") postId: Long
    ): Response<ResponseBody<PatchTogetherResponse>>
}