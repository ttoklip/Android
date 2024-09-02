package com.umc.ttoklip.data.model.news.comment

data class NewsCommentResponse(
    val commentContent: String,
    val commentId: Int,
    val parentId: Int?,
    val writer: String?,
    val writtenTime: String,
    val writerProfileImageUrl:String,
)