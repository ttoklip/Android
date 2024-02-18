package com.umc.ttoklip.data.repository.honeytip

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.honeytip.CreateHoneyTipResponse
import com.umc.ttoklip.data.model.honeytip.HoneyTipMainResponse
import com.umc.ttoklip.data.model.honeytip.InquireHoneyTipResponse
import com.umc.ttoklip.data.model.honeytip.InquireQuestionResponse
import com.umc.ttoklip.data.model.honeytip.request.HoneyTipCommentRequest
import com.umc.ttoklip.data.model.honeytip.request.ReportRequest
import com.umc.ttoklip.data.model.news.comment.NewsCommentRequest
import com.umc.ttoklip.module.NetworkResult
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface HoneyTipRepository {
    //꿀팁
    suspend fun getHoneyTipMain(): NetworkResult<HoneyTipMainResponse>
    suspend fun createHoneyTip(
        title: RequestBody,
        content: RequestBody,
        category: RequestBody,
        images: Array<MultipartBody.Part>,
        url: RequestBody
    ): NetworkResult<CreateHoneyTipResponse>

    suspend fun inquireHoneyTip(honeyTipId: Int): NetworkResult<InquireHoneyTipResponse>

    suspend fun deleteHoneyTip(honeyTipId: Int): NetworkResult<CreateHoneyTipResponse>

    suspend fun editHoneyTip(
        honeyTipId: Int,
        title: RequestBody,
        content: RequestBody,
        category: RequestBody,
        images: Array<MultipartBody.Part>,
        url: RequestBody
    ): NetworkResult<CreateHoneyTipResponse>

    suspend fun reportHoneyTip(
        honeyTipId: Int,
        request: ReportRequest
    ): NetworkResult<CreateHoneyTipResponse>

    suspend fun scrapHoneyTip(honeyTipId: Int): NetworkResult<CreateHoneyTipResponse>

    suspend fun deleteScrapHoneyTip(honeyTipId: Int): NetworkResult<CreateHoneyTipResponse>

    suspend fun likeHoneyTip(honeyTipId: Int): NetworkResult<CreateHoneyTipResponse>

    suspend fun deleteLikeHoneyTip(honeyTipId: Int): NetworkResult<CreateHoneyTipResponse>

    //꿀팁 댓글
    suspend fun postCommentHoneyTip(honeyTipId: Int, request : HoneyTipCommentRequest): NetworkResult<CreateHoneyTipResponse>

    suspend fun postReportCommentHoneyTip(commentId: Int, request: ReportRequest): NetworkResult<CreateHoneyTipResponse>

    suspend fun deleteCommentHoneyTip(commentId: Int): NetworkResult<CreateHoneyTipResponse>

    //질문
    suspend fun createQuestion(
        title: RequestBody,
        content: RequestBody,
        category: RequestBody,
        images: Array<MultipartBody.Part>
    ): NetworkResult<CreateHoneyTipResponse>

    suspend fun inquireQuestion(questionId: Int): NetworkResult<InquireQuestionResponse>

    suspend fun reportQuestion(
        questionId: Int,
        request: ReportRequest
    ): NetworkResult<CreateHoneyTipResponse>

    //질문 댓글
    suspend fun postCommentQuestion(questionId: Int, request : HoneyTipCommentRequest): NetworkResult<CreateHoneyTipResponse>
    suspend fun postReportCommentQuestion(commentId: Int, request: ReportRequest): NetworkResult<CreateHoneyTipResponse>

    suspend fun deleteCommentQuestion(commentId: Int): NetworkResult<CreateHoneyTipResponse>


    suspend fun postLikeAtQuestionComment(commentId: Int): NetworkResult<CreateHoneyTipResponse>


    suspend fun deleteLikeAtQuestionComment(commentId: Int): NetworkResult<CreateHoneyTipResponse>

}