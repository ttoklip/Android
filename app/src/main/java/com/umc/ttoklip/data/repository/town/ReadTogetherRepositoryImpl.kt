package com.umc.ttoklip.data.repository.town

import com.umc.ttoklip.data.api.ReadTogetherApi
import com.umc.ttoklip.data.model.CommonResponse
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.StandardResponse
import com.umc.ttoklip.data.model.town.CreateCommentRequest
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
    ): NetworkResult<Unit> {
        return handleApi({
            api.createTogetherComment(
                postId = postId,
                body = body
            )
        }) { response: ResponseBody<Unit> -> response.result }
    }

    override suspend fun deleteTogetherComment(commentId: Long): NetworkResult<Unit> {
        return handleApi({ api.deleteTogetherComment(commentId) }) { response: ResponseBody<Unit> -> response.result }
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
    ): NetworkResult<Unit> {
        return handleApi({
            api.reportTogetherComment(
                commentId = commentId,
                body = body
            )
        }) { response: ResponseBody<Unit> -> response.result }
    }

    override suspend fun joinTogether(postId: Long): NetworkResult<CommonResponse> {
        return handleApi({ api.joinTogether(postId) }) { response: ResponseBody<CommonResponse> -> response.result }
    }

    override suspend fun cancelTogether(postId: Long): NetworkResult<CommonResponse> {
        return handleApi({ api.cancelTogether(postId) }) { response: ResponseBody<CommonResponse> -> response.result }
    }
}