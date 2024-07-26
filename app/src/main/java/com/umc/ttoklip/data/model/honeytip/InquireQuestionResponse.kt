package com.umc.ttoklip.data.model.honeytip

import com.umc.ttoklip.data.model.question.QuestionCommentResponse

data class InquireQuestionResponse(
    val questionId: Int,
    val title: String,
    val content: String,
    val writer: String?,
    val writtenTime: String,
    val category: String,
    val commentCount: Int,
    val imageUrls: List<ImageUrl>,
    val questionCommentResponses: List<QuestionCommentResponse>,
    val likedByCurrentUser: Boolean
)