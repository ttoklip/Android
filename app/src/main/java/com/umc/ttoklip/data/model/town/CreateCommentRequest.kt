package com.umc.ttoklip.data.model.town

data class CreateCommentRequest(
    val content: String,
    val parentCommentId: Long
)
