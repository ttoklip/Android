package com.umc.ttoklip.data.repository.town

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
import com.umc.ttoklip.module.NetworkResult

interface TownRepository {
    //소통해요 생성 및 신고
    suspend fun createComms(body: CreateCommunicationsRequest): NetworkResult<CreateCommunicationsResponse>
    suspend fun addCommsScrap(postId: Long): NetworkResult<StandardResponse>
    suspend fun cancelCommsScrap(postId: Long): NetworkResult<StandardResponse>
    suspend fun reportComms(postId: Long, body: ReportRequest): NetworkResult<StandardResponse>
    suspend fun addCommsLike(postId: Long): NetworkResult<StandardResponse>
    suspend fun cancelCommsLike(postId: Long): NetworkResult<StandardResponse>
    suspend fun createCommsComment(postId: Long, body: CreateCommentRequest): NetworkResult<Unit>
    suspend fun reportCommsComment(commentId: Long, body: ReportRequest): NetworkResult<Unit>

    //함께해요 생성 및 신고
    suspend fun createTogether(body: CreateTogethersRequest): NetworkResult<CreateTogethersResponse>
    suspend fun reportTogether(postId: Long, body: ReportRequest): NetworkResult<StandardResponse>
    suspend fun createTogetherComment(postId: Long, body: CreateCommentRequest): NetworkResult<Unit>
    suspend fun reportTogetherComment(commentId: Long, body: ReportRequest): NetworkResult<Unit>

    //소통해요 글 조회, 수정 및 삭제
    suspend fun viewComms(postId: Long): NetworkResult<ViewCommunicationResponse>
    suspend fun deleteComms(postId: Long): NetworkResult<DeleteCommunicationResponse>
    suspend fun patchComms(
        postId: Long,
        body: CreateCommunicationsRequest
    ): NetworkResult<PatchCommunicationResponse>

    suspend fun deleteCommsComment(commentId: Long): NetworkResult<Unit>

    //함께해요 글 조회, 수정 및 상태 수정
    suspend fun viewTogether(postId: Long): NetworkResult<ViewTogetherResponse>
    suspend fun patchTogether(
        postId: Long,
        body: CreateTogethersRequest
    ): NetworkResult<PatchTogetherResponse>

    suspend fun patchTogetherStatus(
        postId: Long,
        body: PatchTogetherStatusRequest
    ): NetworkResult<StandardResponse>

    suspend fun deleteTogetherComment(commentId: Long): NetworkResult<Unit>
}