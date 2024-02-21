package com.umc.ttoklip.data.model.mypage

data class HoneyTip(
    val content: String,
    val id: Int,
    val title: String,
    val category: String,
    val writer: String,
    val likeCount: Int,
    val scrapCount: Int,
    val commentCount: Int
)