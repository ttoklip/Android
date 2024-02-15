package com.umc.ttoklip.data.model.town

data class ViewTogetherResponse(
    val author: String,
    val cartId: Long,
    val content: String,
    val imageUrls: List<ImageUrl>,
    val title: String,
    val writtenTime: String,
    val scrapCount: Long,
    val likeCount: Long,
    val commentCount: Int,
    val commentResponse: CommentResponse,
    val likedByCurrentUser: Boolean,
    val scrapedByCurrentUser: Boolean
)