package com.umc.ttoklip.data.model.news.comment

data class NewsCommentRequest(
    val comment: String,
    val parentCommentId: Int
)