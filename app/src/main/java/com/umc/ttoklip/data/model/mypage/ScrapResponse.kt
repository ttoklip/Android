package com.umc.ttoklip.data.model.mypage

data class ScrapResponse(
    val category: String,
    val commentCount: Int,
    val content: String,
    val id: Int,
    val likeCount: Int,
    val scrapCount: Int,
    val title: String,
    val writer: String
)