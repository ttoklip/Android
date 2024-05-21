package com.umc.ttoklip.data.repository.town

import com.umc.ttoklip.data.model.CommonResponse
import com.umc.ttoklip.data.model.StandardResponse
import com.umc.ttoklip.data.model.town.CommentResponse
import com.umc.ttoklip.data.model.town.CreateCommentRequest
import com.umc.ttoklip.data.model.town.ReportRequest
import com.umc.ttoklip.data.model.town.ViewTogetherResponse
import com.umc.ttoklip.module.NetworkResult

interface ReadTogetherRepository {
    suspend fun viewTogether(postId: Long): NetworkResult<ViewTogetherResponse>
    suspend fun createTogetherComment(postId: Long, body: CreateCommentRequest): NetworkResult<CommentResponse>
    suspend fun deleteTogetherComment(commentId: Long): NetworkResult<CommentResponse>
    suspend fun reportTogether(postId: Long, body: ReportRequest): NetworkResult<StandardResponse>
    suspend fun reportTogetherComment(commentId: Long, body: ReportRequest): NetworkResult<CommentResponse>
    suspend fun joinTogether(postId: Long): NetworkResult<CommonResponse>

    suspend fun cancelTogether(postId: Long): NetworkResult<CommonResponse>
}