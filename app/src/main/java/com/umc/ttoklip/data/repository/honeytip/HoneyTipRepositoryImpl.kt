package com.umc.ttoklip.data.repository.honeytip

import com.umc.ttoklip.data.api.HoneyTipApi
import com.umc.ttoklip.data.model.honeytip.CreateHoneyTipResponse
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.honeytip.HoneyTipMainResponse
import com.umc.ttoklip.data.model.honeytip.HoneyTipPagingResponse
import com.umc.ttoklip.data.model.honeytip.InquireHoneyTipResponse
import com.umc.ttoklip.data.model.honeytip.InquireQuestionResponse
import com.umc.ttoklip.data.model.honeytip.request.HoneyTipCommentRequest
import com.umc.ttoklip.data.model.honeytip.request.ReportRequest
import com.umc.ttoklip.data.model.news.comment.NewsCommentRequest
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.handleApi
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import javax.inject.Inject

class HoneyTipRepositoryImpl @Inject constructor(
    private val api: HoneyTipApi
) : HoneyTipRepository {
    override suspend fun getHoneyTipMain(): NetworkResult<HoneyTipMainResponse> {
        return handleApi({ api.getHoneyTipMain() }) { response: ResponseBody<HoneyTipMainResponse> -> response.result }
    }

    override suspend fun getQuestionByCategory(
        category: String,
        page: Int
    ): NetworkResult<HoneyTipPagingResponse> {
        return handleApi({ api.getQuestionByCategory(category, page) }) { response: ResponseBody<HoneyTipPagingResponse> -> response.result }
    }

    override suspend fun getHoneyTipByCategory(
        category: String,
        page: Int
    ): NetworkResult<HoneyTipPagingResponse> {
        return handleApi({ api.getHoneyTipByCategory(category, page) }) { response: ResponseBody<HoneyTipPagingResponse> -> response.result }
    }

    //꿀팁
    override suspend fun createHoneyTip(
        title: RequestBody,
        content: RequestBody,
        category: RequestBody,
        images: List<MultipartBody.Part?>,
        uri: RequestBody
    ): NetworkResult<CreateHoneyTipResponse> {
        return handleApi({
            api.postNewHoneyTip(title, content, category, images, uri)
        }) { response: ResponseBody<CreateHoneyTipResponse> -> response.result }
    }

    override suspend fun inquireHoneyTip(honeyTipId: Int): NetworkResult<InquireHoneyTipResponse> {
        return handleApi({ api.getHoneyTip(honeyTipId) }) { response: ResponseBody<InquireHoneyTipResponse> -> response.result }
    }

    override suspend fun deleteHoneyTip(honeyTipId: Int): NetworkResult<CreateHoneyTipResponse> {
        return handleApi({ api.deleteHoneyTip(honeyTipId) }) { response: ResponseBody<CreateHoneyTipResponse> -> response.result }
    }

    override suspend fun editHoneyTip(
        honeyTipId: Int,
        title: RequestBody,
        content: RequestBody,
        category: RequestBody,
        deleteImageIds: RequestBody,
        addImages: List<MultipartBody.Part?>,
        url: RequestBody
    ): NetworkResult<CreateHoneyTipResponse> {
        return handleApi({ api.patchHoneyTip(honeyTipId, title, content, category, deleteImageIds, addImages, url) })
        { response: ResponseBody<CreateHoneyTipResponse> -> response.result }
    }

    override suspend fun reportHoneyTip(
        honeyTipId: Int,
        request: ReportRequest
    ): NetworkResult<CreateHoneyTipResponse> {
        return handleApi({
            api.postReportHoneyTip(honeyTipId, ReportRequest(request.content, request.reportType))
        }) { response: ResponseBody<CreateHoneyTipResponse> -> response.result }
    }

    override suspend fun scrapHoneyTip(honeyTipId: Int): NetworkResult<CreateHoneyTipResponse> {
        return handleApi({api.postHoneyTipScrap(honeyTipId)}) { response: ResponseBody<CreateHoneyTipResponse> -> response.result}
    }

    override suspend fun deleteScrapHoneyTip(honeyTipId: Int): NetworkResult<CreateHoneyTipResponse> {
        return handleApi({api.deleteHoneyTipScrap(honeyTipId)}) { response: ResponseBody<CreateHoneyTipResponse> -> response.result}
    }

    override suspend fun likeHoneyTip(honeyTipId: Int): NetworkResult<CreateHoneyTipResponse> {
        return handleApi({api.postLikeHoneyTip(honeyTipId)}) { response: ResponseBody<CreateHoneyTipResponse> -> response.result}
    }

    override suspend fun deleteLikeHoneyTip(honeyTipId: Int): NetworkResult<CreateHoneyTipResponse> {
        return handleApi({api.deleteLikeHoneyTip(honeyTipId)}) { response: ResponseBody<CreateHoneyTipResponse> -> response.result}
    }

    override suspend fun postCommentHoneyTip(
        honeyTipId: Int,
        request: HoneyTipCommentRequest
    ): NetworkResult<CreateHoneyTipResponse> {
        return handleApi({api.postCommentHoneyTip(honeyTipId, request)}) { response: ResponseBody<CreateHoneyTipResponse> -> response.result}
    }

    override suspend fun postReportCommentHoneyTip(commentId: Int, request: ReportRequest): NetworkResult<CreateHoneyTipResponse> {
        return handleApi({api.postReportCommentHoneyTip(commentId, request)}) { response: ResponseBody<CreateHoneyTipResponse> -> response.result}
    }

    override suspend fun deleteCommentHoneyTip(commentId: Int): NetworkResult<CreateHoneyTipResponse> {
        return handleApi({api.deleteCommentHoneyTip(commentId)}) { response: ResponseBody<CreateHoneyTipResponse> -> response.result}
    }


    //질문

    override suspend fun createQuestion(
        title: RequestBody,
        content: RequestBody,
        category: RequestBody,
        images: List<MultipartBody.Part?>
    ): NetworkResult<CreateHoneyTipResponse> {
        return handleApi({
            api.postNewQuestion(title, content, category, images)
        }) { response: ResponseBody<CreateHoneyTipResponse> -> response.result }
    }

    override suspend fun inquireQuestion(questionId: Int): NetworkResult<InquireQuestionResponse> {
        return handleApi({ api.getQuestion(questionId) }) { response: ResponseBody<InquireQuestionResponse> -> response.result }
    }

    override suspend fun reportQuestion(
        questionId: Int,
        request: ReportRequest
    ): NetworkResult<CreateHoneyTipResponse> {
        return handleApi({
            api.postReportQuestion(
                questionId,
                ReportRequest(request.content, request.reportType)
            )
        })
        { response: ResponseBody<CreateHoneyTipResponse> -> response.result }
    }

    override suspend fun postCommentQuestion(
        questionId: Int,
        request: HoneyTipCommentRequest
    ): NetworkResult<CreateHoneyTipResponse> {
        return handleApi({api.postCommentQuestion(questionId, request)}) { response: ResponseBody<CreateHoneyTipResponse> -> response.result}
    }

    override suspend fun postReportCommentQuestion(commentId: Int, request: ReportRequest): NetworkResult<CreateHoneyTipResponse> {
        return handleApi({api.postReportCommentQuestion(commentId, request)}) { response: ResponseBody<CreateHoneyTipResponse> -> response.result}
    }

    override suspend fun deleteCommentQuestion(commentId: Int): NetworkResult<CreateHoneyTipResponse> {
        return handleApi({api.deleteCommentQuestion(commentId)}) { response: ResponseBody<CreateHoneyTipResponse> -> response.result}
    }

    override suspend fun postLikeAtQuestionComment(commentId: Int): NetworkResult<CreateHoneyTipResponse> {
        return handleApi({api.postLikeAtQuestionComment(commentId)}) { response: ResponseBody<CreateHoneyTipResponse> -> response.result}
    }

    override suspend fun deleteLikeAtQuestionComment(commentId: Int): NetworkResult<CreateHoneyTipResponse> {
        return handleApi({api.deleteLikeAtQuestionComment(commentId)}) { response: ResponseBody<CreateHoneyTipResponse> -> response.result}
    }


}