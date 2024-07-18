package com.umc.ttoklip.data.repository.town

import com.umc.ttoklip.data.api.ReadTogetherApi
import com.umc.ttoklip.data.model.CommonResponse
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.StandardResponse
import com.umc.ttoklip.data.model.town.CommentResponse
import com.umc.ttoklip.data.model.town.CreateCommentRequest
import com.umc.ttoklip.data.model.town.ParticipantsResponse
import com.umc.ttoklip.data.model.town.PatchCartStatusRequest
import com.umc.ttoklip.data.model.town.ReportRequest
import com.umc.ttoklip.data.model.town.ViewTogetherResponse
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import javax.inject.Inject

class ReadTogetherRepositoryImpl @Inject constructor(private val api: ReadTogetherApi) :
    ReadTogetherRepository {
    override suspend fun viewTogether(postId: Long): NetworkResult<ViewTogetherResponse> {
        return handleApi({ api.viewTogethers(postId) }) { response: ResponseBody<ViewTogetherResponse> -> response.result }
    }

    override suspend fun createTogetherComment(
        postId: Long,
        body: CreateCommentRequest
    ): NetworkResult<CommentResponse> {
        return handleApi({
            api.createTogetherComment(
                postId = postId,
                body = body
            )
        }) { response: ResponseBody<CommentResponse> -> response.result }
    }

    override suspend fun deleteTogetherComment(commentId: Long): NetworkResult<CommentResponse> {
        return handleApi({ api.deleteTogetherComment(commentId) }) { response: ResponseBody<CommentResponse> -> response.result }
    }

    override suspend fun reportTogether(
        postId: Long,
        body: ReportRequest
    ): NetworkResult<StandardResponse> {
        return handleApi({
            api.reportTogethers(
                postId = postId,
                body = body
            )
        }) { response: ResponseBody<StandardResponse> -> response.result }
    }

    override suspend fun reportTogetherComment(
        commentId: Long,
        body: ReportRequest
    ): NetworkResult<CommentResponse> {
        return handleApi({
            api.reportTogetherComment(
                commentId = commentId,
                body = body
            )
        }) { response: ResponseBody<CommentResponse> -> response.result }
    }

    override suspend fun joinTogether(postId: Long): NetworkResult<CommonResponse> {
        return handleApi({ api.joinTogether(postId) }) { response: ResponseBody<CommonResponse> -> response.result }
    }

    override suspend fun cancelTogether(postId: Long): NetworkResult<CommonResponse> {
        return handleApi({ api.cancelTogether(postId) }) { response: ResponseBody<CommonResponse> -> response.result }
    }

    override suspend fun fetchParticipantsCount(postId: Long): NetworkResult<CommonResponse> {
        return handleApi({api.fetchParticipantsCount(postId)}) {response: ResponseBody<CommonResponse> -> response.result}
    }

    override suspend fun patchPostStatus(postId: Long, request: PatchCartStatusRequest): NetworkResult<CommonResponse> {
        return handleApi({api.patchPostStatus(postId, request)}) {response: ResponseBody<CommonResponse> -> response.result}
    }

    override suspend fun fetchParticipants(cartId: Int): NetworkResult<ParticipantsResponse> {
        return handleApi({api.fetchParticipants(cartId)}) {response: ResponseBody<ParticipantsResponse> -> response.result}
    }
}