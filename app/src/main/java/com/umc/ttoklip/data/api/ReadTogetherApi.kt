package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.CommonResponse
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.StandardResponse
import com.umc.ttoklip.data.model.town.CommentResponse
import com.umc.ttoklip.data.model.town.CreateCommentRequest
import com.umc.ttoklip.data.model.town.ParticipantsResponse
import com.umc.ttoklip.data.model.town.PatchCartStatusRequest
import com.umc.ttoklip.data.model.town.ReportRequest
import com.umc.ttoklip.data.model.town.ViewTogetherResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ReadTogetherApi {
    //함께해요 게시글 조회
    @GET("/api/v1/town/carts/{postId}")
    suspend fun viewTogethers(
        @Path("postId") postId: Long
    ): Response<ResponseBody<ViewTogetherResponse>>


    //함께해요 댓글 생성
    @POST("/api/v1/town/carts/comment/{postId}")
    suspend fun createTogetherComment(
        @Body body: CreateCommentRequest,
        @Path("postId") postId: Long
    ): Response<ResponseBody<CommentResponse>>

    //함께해요 댓글 삭제
    @DELETE("/api/v1/town/carts/comment/{commentId}")
    suspend fun deleteTogetherComment(
        @Path("commentId") commentId: Long
    ): Response<ResponseBody<CommentResponse>>

    //함께해요 글 신고
    @POST("/api/v1/town/carts/report/{postId}")
    suspend fun reportTogethers(
        @Body body: ReportRequest,
        @Path("postId") postId: Long
    ): Response<ResponseBody<StandardResponse>>

    //함께해요 댓글 신고
    @POST("/api/v1/town/carts/comment/report/{commentId}")
    suspend fun reportTogetherComment(
        @Body body: ReportRequest,
        @Path("commentId") commentId: Long
    ): Response<ResponseBody<CommentResponse>>

    @POST("/api/v1/town/carts/participants/{cartId}")
    suspend fun joinTogether(
        @Path("cartId") postId: Long
    ): Response<ResponseBody<CommonResponse>>

    @DELETE("/api/v1/town/carts/participants/{cartId}")
    suspend fun cancelTogether(
        @Path("cartId") postId: Long
    ): Response<ResponseBody<CommonResponse>>

    @GET("/api/v1/town/carts/participants/count/{cartId}")
    suspend fun fetchParticipantsCount(
        @Path("cartId") postId: Long
    ): Response<ResponseBody<CommonResponse>>

    @PATCH("/api/v1/town/carts/{postId}/status")
    suspend fun patchPostStatus(
        @Path("postId") postId: Long,
        @Body request: PatchCartStatusRequest
    ): Response<ResponseBody<CommonResponse>>

    @GET("/api/v1/town/carts/participants/members/{cartId}")
    suspend fun fetchParticipants(
        @Path("cartId") cartId: Int
    ): Response<ResponseBody<ParticipantsResponse>>
}