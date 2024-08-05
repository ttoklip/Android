package com.umc.ttoklip.data.model.town

data class CommentResponse(
    val commentId: Long,
    val commentContent: String,
    val parentId: Long,
    val writer: String,
    val writtenTime: String,
    val writerProfileImageUrl: String,
)
