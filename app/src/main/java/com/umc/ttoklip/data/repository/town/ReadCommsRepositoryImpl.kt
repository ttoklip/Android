package com.umc.ttoklip.data.repository.town

import com.umc.ttoklip.data.api.ReadCommsApi
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.StandardResponse
import com.umc.ttoklip.data.model.town.CreateCommentRequest
import com.umc.ttoklip.data.model.town.DeleteCommunicationResponse
import com.umc.ttoklip.data.model.town.ReportRequest
import com.umc.ttoklip.data.model.town.ViewCommunicationResponse
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import javax.inject.Inject

class ReadCommsRepositoryImpl @Inject constructor(private val api: ReadCommsApi) :
    ReadCommsRepository {
    override suspend fun viewComms(postId: Long): NetworkResult<ViewCommunicationResponse> {
        return handleApi({ api.viewCommunications(postId) }) { response: ResponseBody<ViewCommunicationResponse> -> response.result }
    }

    override suspend fun deleteComms(postId: Long): NetworkResult<DeleteCommunicationResponse> {
        return handleApi({ api.deleteCommunications(postId) }) { response: ResponseBody<DeleteCommunicationResponse> -> response.result }
    }

    override suspend fun addCommsScrap(postId: Long): NetworkResult<StandardResponse> {
        return handleApi({ api.addCommunicationScrap(postId) }) { response: ResponseBody<StandardResponse> -> response.result }
    }

    override suspend fun cancelCommsScrap(postId: Long): NetworkResult<StandardResponse> {
        return handleApi({ api.cancelCommunicationScrap(postId) }) { response: ResponseBody<StandardResponse> -> response.result }
    }

    override suspend fun reportComms(
        postId: Long,
        body: ReportRequest
    ): NetworkResult<StandardResponse> {
        return handleApi({
            api.reportCommunications(
                postId = postId,
                body = body
            )
        }) { response: ResponseBody<StandardResponse> -> response.result }
    }

    override suspend fun addCommsLike(postId: Long): NetworkResult<StandardResponse> {
        return handleApi({ api.addCommunicationLike(postId) }) { response: ResponseBody<StandardResponse> -> response.result }
    }

    override suspend fun cancelCommsLike(postId: Long): NetworkResult<StandardResponse> {
        return handleApi({ api.cancelCommunicationLike(postId) }) { response: ResponseBody<StandardResponse> -> response.result }
    }

    override suspend fun createCommsComment(
        postId: Long,
        body: CreateCommentRequest
    ): NetworkResult<Unit> {
        return handleApi({
            api.createCommunicationComment(
                postId = postId,
                body = body
            )
        }) { response: ResponseBody<Unit> -> response.result }
    }

    override suspend fun reportCommsComment(
        commentId: Long,
        body: com.umc.ttoklip.data.model.honeytip.request.ReportRequest
    ): NetworkResult<Unit> {
        return handleApi({
            api.reportCommunicationComment(
                commentId = commentId,
                body = body
            )
        }) { response: ResponseBody<Unit> -> response.result }
    }


    override suspend fun deleteCommsComment(commentId: Long): NetworkResult<Unit> {
        return handleApi({ api.deleteCommunicationComment(commentId) }) { response: ResponseBody<Unit> -> response.result }
    }
}