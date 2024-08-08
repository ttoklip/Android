package com.umc.ttoklip.data.model.town

data class ViewTogetherResponse(
    val writer: String,
    val cartId: Long,
    val content: String,
    val imageUrls: List<TownImageUrl>,
    val title: String,
    val writtenTime: String,
    val totalPrice: Long,
    val partyCnt: Long,
    val partyMax: Long,
    val location: String,
    val chatUrl: String,
    val currentPrice: Long,
    val commentResponses: List<CommentResponse>,
    val status: String,
    val alreadyJoin : Boolean,
    val writerProfileImageUrl: String,
)