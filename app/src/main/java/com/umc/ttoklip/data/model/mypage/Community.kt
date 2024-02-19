package com.umc.ttoklip.data.model.mypage

data class Community(
    val content: String,
    val id: Int,
    val title: String,
    val writer: String,
    val category: String,
    val commentCount: Int
)