package com.umc.ttoklip.presentation.hometown

import com.umc.ttoklip.data.model.town.CreateCommentRequest
import com.umc.ttoklip.data.model.town.ReportRequest
import com.umc.ttoklip.data.model.town.ViewCommunicationResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface ReadCommunicationViewModel {
    val postId: StateFlow<Long>
    val postContent: StateFlow<ViewCommunicationResponse>
    val like: StateFlow<Boolean>
    val scrap: StateFlow<Boolean>
    val replyCommentParentId : MutableStateFlow<Int>

    fun savePostId(postId: Long)
    fun readCommunication(postId: Long)
    fun changeScrap()
    fun changeLike()
    fun reportPost(reportRequest: ReportRequest)
    fun reportComment(commentId: Long, reportRequest: com.umc.ttoklip.data.model.honeytip.request.ReportRequest)
    fun deleteComment(commentId: Long)
    fun createComment(body: CreateCommentRequest)

}