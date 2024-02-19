package com.umc.ttoklip.data.model.town

data class ViewCommunicationResponse(
    val writer: String,
    val communityId: Long,
    val content: String,
    val imageUrls: List<ImageUrl>,
    val title: String,
    val writtenTime: String,
    val scrapCount: Long,
    val likeCount: Long,
    val commentCount: Int,
    val commentResponses: List<CommentResponse>,
    val likedByCurrentUser: Boolean,
    val scrapedByCurrentUser: Boolean
)