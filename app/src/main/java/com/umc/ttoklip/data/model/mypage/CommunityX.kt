package com.umc.ttoklip.data.model.mypage

data class CommunityX(
    val id: Int,
    val content: String,
    val title: String,
    val writer: String,
    val commentCount: Int,
    val likeCount: Int,
    val scrapCount: Int,
    val writerProfileImageUrl:String
)