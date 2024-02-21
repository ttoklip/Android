package com.umc.ttoklip.data.model.town

data class CreateCommentRequest(
    val comment: String,
    val parentCommentId: Long
)
