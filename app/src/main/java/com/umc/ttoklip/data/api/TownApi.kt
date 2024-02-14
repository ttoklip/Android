package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.StandardResponse
import com.umc.ttoklip.data.model.town.CreateCommentRequest
import com.umc.ttoklip.data.model.town.CreateCommunicationsRequest
import com.umc.ttoklip.data.model.town.CreateCommunicationsResponse
import com.umc.ttoklip.data.model.town.CreateTogethersRequest
import com.umc.ttoklip.data.model.town.CreateTogethersResponse
import com.umc.ttoklip.data.model.town.DeleteCommunicationResponse
import com.umc.ttoklip.data.model.town.PatchCommunicationResponse
import com.umc.ttoklip.data.model.town.PatchTogetherResponse
import com.umc.ttoklip.data.model.town.PatchTogetherStatusRequest
import com.umc.ttoklip.data.model.town.ReportRequest
import com.umc.ttoklip.data.model.town.ViewCommunicationResponse
import com.umc.ttoklip.data.model.town.ViewTogetherResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface TownApi {
    //소통해요 게시글 생성
    @POST("/api/v1/town/comms")
    suspend fun createCommunications(
        @Body body: CreateCommunicationsRequest
    ): Response<ResponseBody<CreateCommunicationsResponse>>

    //소통해요 댓글 생성
    @POST("/api/v1/town/comms/comment/{postId}")
    suspend fun createCommunicationComment(
        @Body body: CreateCommentRequest,
        @Path("postId") postId: Long
    ): Response<ResponseBody<Unit>>

    //함꼐해요 게시글 생성
    @POST("/api/v1/town/carts")
    suspend fun createTogethers(
        @Body body: CreateTogethersRequest
    ): Response<ResponseBody<CreateTogethersResponse>>

    //함께해요 댓글 생성
    @POST("/api/v1/town/carts/comment/{postId}")
    suspend fun createTogetherComment(
        @Body body: CreateCommentRequest,
        @Path("postId") postId: Long
    ): Response<ResponseBody<Unit>>

    //소통해요 댓글 삭제
    @DELETE("/api/v1/town/comms/comment/{commentId}")
    suspend fun deleteCommunicationComment(
        @Path("commentId") commentId: Long
    ): Response<ResponseBody<Unit>>

    //함께해요 댓글 삭제
    @DELETE("/api/v1/town/carts/comment/{commentId}")
    suspend fun deleteTogetherComment(
        @Path("commentId") commentId: Long
    ): Response<ResponseBody<Unit>>

    //소통해요 스크랩 추가
    @POST("/api/v1/town/comms/scrap/{postId}")
    suspend fun addCommunicationScrap(
        @Path("postId") postId: Long
    ): Response<ResponseBody<StandardResponse>>

    //소통해요 스크랩 취소
    @DELETE("/api/v1/town/comms/scrap/{postId}")
    suspend fun cancelCommunicationScrap(
        @Path("postId") postId: Long
    ): Response<ResponseBody<StandardResponse>>

    //소통해요 좋아요 추가
    @POST("/api/v1/town/comms/like/{postId}")
    suspend fun addCommunicationLike(
        @Path("postId") postId: Long
    ): Response<ResponseBody<StandardResponse>>

    //소통해요 좋아요 취소
    @DELETE("/api/v1/town/comms/like/{postId}")
    suspend fun cancelCommunicationLike(
        @Path("postId") postId: Long
    ): Response<ResponseBody<StandardResponse>>


    //reportType enum으로 생성 필요
    //소통해요 글 신고
    @POST("/api/v1/town/comms/report/{postId}")
    suspend fun reportCommunications(
        @Body body: ReportRequest,
        @Path("postId") postId: Long
    ): Response<ResponseBody<StandardResponse>>

    //소통해요 댓글 신고
    @POST("/api/v1/town/comms/comment/report/{postId}")
    suspend fun reportCommunicationComment(
        @Body body: ReportRequest,
        @Path("commentId") commentId: Long
    ): Response<ResponseBody<Unit>>

    //함께해요 글 신고
    @POST("/api/v1/town/carts/report/{postId}")
    suspend fun reportTogethers(
        @Body body: ReportRequest,
        @Path("postId") postId: Long
    ): Response<ResponseBody<StandardResponse>>

    //함께해요 댓글 신고
    @POST("/api/v1/town/carts/comment/report/{postId}")
    suspend fun reportTogetherComment(
        @Body body: ReportRequest,
        @Path("commentId") commentId: Long
    ): Response<ResponseBody<Unit>>

    //소통해요 게시글 조회
    @GET("/api/v1/town/comms/{postId}")
    suspend fun viewCommunications(
        @Path("postId") postId: Long
    ): Response<ResponseBody<ViewCommunicationResponse>>

    //소통해요 게시글 삭제
    @DELETE("/api/v1/town/comms/{postId}")
    suspend fun deleteCommunications(
        @Path("postId") postId: Long
    ): Response<ResponseBody<DeleteCommunicationResponse>>

    //소통해요 게시글 수정
    @PATCH("/api/v1/town/comms/{postId}")
    suspend fun patchCommunications(
        @Body body: CreateCommunicationsRequest,
        @Path("postId") postId: Long
    ): Response<ResponseBody<PatchCommunicationResponse>>

    //함께해요 게시글 조회
    @GET("/api/v1/town/carts/{postId}")
    suspend fun viewTogethers(
        @Path("postId") postId: Long
    ): Response<ResponseBody<ViewTogetherResponse>>

    //함께해요 게시글 수정
    @PATCH("/api/v1/town/carts/{postId}")
    suspend fun patchTogethers(
        @Body body: CreateTogethersRequest,
        @Path("postId") postId: Long
    ): Response<ResponseBody<PatchTogetherResponse>>

    //함께해요 게시글 상태 수정
    @PATCH("/api/v1/town/carts/{postId}/status")
    suspend fun patchTogethersStatus(
        @Body body: PatchTogetherStatusRequest,
        @Path("postId") postId: Long
    ): Response<ResponseBody<StandardResponse>>


}