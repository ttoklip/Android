package com.umc.ttoklip.data.model.question

data class QuestionCommentResponse(
    val commentContent: String,
    val commentId: Int,
    val parentId: Int?,
    val writer: String?,
    val writtenTime: String,
    var likedByCurrentUser: Boolean,
    val writerProfileImageUrl:String,
)
