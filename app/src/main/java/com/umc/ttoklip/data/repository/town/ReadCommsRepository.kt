package com.umc.ttoklip.data.repository.town

import com.umc.ttoklip.data.model.StandardResponse
import com.umc.ttoklip.data.model.town.CreateCommentRequest
import com.umc.ttoklip.data.model.town.DeleteCommunicationResponse
import com.umc.ttoklip.data.model.town.ReportRequest
import com.umc.ttoklip.data.model.town.ViewCommunicationResponse
import com.umc.ttoklip.module.NetworkResult

interface ReadCommsRepository {
    suspend fun viewComms(postId: Long): NetworkResult<ViewCommunicationResponse>
    suspend fun deleteComms(postId: Long): NetworkResult<DeleteCommunicationResponse>
    suspend fun addCommsScrap(postId: Long): NetworkResult<StandardResponse>
    suspend fun cancelCommsScrap(postId: Long): NetworkResult<StandardResponse>
    suspend fun reportComms(postId: Long, body: ReportRequest): NetworkResult<StandardResponse>
    suspend fun addCommsLike(postId: Long): NetworkResult<StandardResponse>
    suspend fun cancelCommsLike(postId: Long): NetworkResult<StandardResponse>
    suspend fun createCommsComment(postId: Long, body: CreateCommentRequest): NetworkResult<Unit>
    suspend fun reportCommsComment(commentId: Long, body: com.umc.ttoklip.data.model.honeytip.request.ReportRequest): NetworkResult<Unit>
    suspend fun deleteCommsComment(commentId: Long): NetworkResult<Unit>
}