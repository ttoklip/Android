package com.umc.ttoklip.data.model.town

data class ViewTogetherResponse(
    val writer: String,
    val cartId: Long,
    val content: String,
    val imageUrls: List<ImageUrl>,
    val title: String,
    val writtenTime: String,
    val commentResponse: List<CommentResponse>,
    val status: String
)