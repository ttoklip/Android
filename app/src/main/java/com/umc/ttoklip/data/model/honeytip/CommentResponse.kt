package com.umc.ttoklip.data.model.honeytip

data class CommentResponse(
    val commentId: Int,
    val commentContent: String,
    val parentId: Int,
    val writer: String,
    val writtenTime: String
)
