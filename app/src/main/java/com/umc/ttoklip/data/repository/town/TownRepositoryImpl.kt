package com.umc.ttoklip.data.repository.town

import com.umc.ttoklip.data.api.TownApi
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
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import javax.inject.Inject

class TownRepositoryImpl @Inject constructor(
    private val api: TownApi
) : TownRepository {
    //소통해요 생성 및 신고
    override suspend fun createComms(body: CreateCommunicationsRequest): NetworkResult<CreateCommunicationsResponse> {
        return handleApi({ api.createCommunications(body) }) { response: ResponseBody<CreateCommunicationsResponse> -> response.result }
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
        body: ReportRequest
    ): NetworkResult<Unit> {
        return handleApi({
            api.reportCommunicationComment(
                commentId = commentId,
                body = body
            )
        }) { response: ResponseBody<Unit> -> response.result }
    }

    //함께해요 생성 및 신고
    override suspend fun createTogether(body: CreateTogethersRequest): NetworkResult<CreateTogethersResponse> {
        return handleApi({ api.createTogethers(body) }) { response: ResponseBody<CreateTogethersResponse> -> response.result }
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

    override suspend fun createTogetherComment(
        postId: Long,
        body: CreateCommentRequest
    ): NetworkResult<Unit> {
        return handleApi({
            api.createTogetherComment(
                postId = postId,
                body = body
            )
        }) { response: ResponseBody<Unit> -> response.result }
    }

    override suspend fun reportTogetherComment(
        commentId: Long,
        body: ReportRequest
    ): NetworkResult<Unit> {
        return handleApi({
            api.reportTogetherComment(
                commentId = commentId,
                body = body
            )
        }) { response: ResponseBody<Unit> -> response.result }
    }

    //소통해요 글 조회, 수정 및 삭제
    override suspend fun viewComms(postId: Long): NetworkResult<ViewCommunicationResponse> {
        return handleApi({ api.viewCommunications(postId) }) { response: ResponseBody<ViewCommunicationResponse> -> response.result }
    }

    override suspend fun deleteComms(postId: Long): NetworkResult<DeleteCommunicationResponse> {
        return handleApi({ api.deleteCommunications(postId) }) { response: ResponseBody<DeleteCommunicationResponse> -> response.result }
    }

    override suspend fun patchComms(
        postId: Long,
        body: CreateCommunicationsRequest
    ): NetworkResult<PatchCommunicationResponse> {
        return handleApi({
            api.patchCommunications(
                postId = postId,
                body = body
            )
        }) { response: ResponseBody<PatchCommunicationResponse> -> response.result }
    }

    override suspend fun deleteCommsComment(commentId: Long): NetworkResult<Unit> {
        return handleApi({ api.deleteCommunicationComment(commentId) }) { response: ResponseBody<Unit> -> response.result }
    }

    //함께해요 글 조회, 수정 및 상태 수정
    override suspend fun viewTogether(postId: Long): NetworkResult<ViewTogetherResponse> {
        return handleApi({ api.viewTogethers(postId) }) { response: ResponseBody<ViewTogetherResponse> -> response.result }
    }

    override suspend fun patchTogether(
        postId: Long,
        body: CreateTogethersRequest
    ): NetworkResult<PatchTogetherResponse> {
        return handleApi({
            api.patchTogethers(
                postId = postId,
                body = body
            )
        }) { response: ResponseBody<PatchTogetherResponse> -> response.result }
    }

    override suspend fun patchTogetherStatus(
        postId: Long,
        body: PatchTogetherStatusRequest
    ): NetworkResult<StandardResponse> {
        return handleApi({
            api.patchTogethersStatus(
                postId = postId,
                body = body
            )
        }) { response: ResponseBody<StandardResponse> -> response.result }
    }

    override suspend fun deleteTogetherComment(commentId: Long): NetworkResult<Unit> {
        return handleApi({ api.deleteTogetherComment(commentId) }) { response: ResponseBody<Unit> -> response.result }
    }
}