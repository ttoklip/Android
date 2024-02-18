package com.umc.ttoklip.data.model.honeytip

data class InquireQuestionResponse(
    val questionId: Int,
    val title: String,
    val content: String,
    val writer: String?,
    val writtenTime: String,
    val category: String,
    val commentCount: Int,
    val imageUrls: List<ImageUrl>,
    val commentResponses: List<CommentResponse>,
    val likedByCurrentUser: Boolean
)