package com.umc.ttoklip.data.model.honeytip

data class CommentResponse(
    val commentContent: String,
    val commentId: Int,
    val parentId: Int?,
    val writer: String?,
    val writtenTime: String,
    val writerProfileImageUrl: String
)
