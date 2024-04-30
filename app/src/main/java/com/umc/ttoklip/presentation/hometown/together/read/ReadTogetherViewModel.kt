package com.umc.ttoklip.presentation.hometown.together.read

import com.umc.ttoklip.data.model.town.CreateCommentRequest
import com.umc.ttoklip.data.model.town.ReportRequest
import com.umc.ttoklip.data.model.town.ViewTogetherResponse
import kotlinx.coroutines.flow.StateFlow

interface ReadTogetherViewModel {
    val joinState: StateFlow<Boolean>
    val deadlineState: StateFlow<Boolean>
    val isWriter: StateFlow<Boolean>
    val postId: StateFlow<Long>
    val writer: StateFlow<String>
    val postContent: StateFlow<ViewTogetherResponse>

    fun joinBtnClick()
    fun savePostId(postId: Long)

    fun checkWriter(writer: String)
    fun readTogether(postId: Long)
    fun reportPost(reportRequest: ReportRequest)
    fun reportComment(commentId: Long, reportRequest: ReportRequest)
    fun deleteComment(commentId: Long)
    fun createComment(body: CreateCommentRequest)
    fun joinTogether()
    fun cancelTogether()
}