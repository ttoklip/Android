package com.umc.ttoklip.data.model.honeytip

data class HoneyTipMain(
    val id: Int,
    val title: String,
    val content: String,
    val writer: String?,
    val likeCount: Int,
    val commentCount: Int,
    val scrapCount: Int,
    val writtenTime: String,
    val writerProfileImageUrl: String,
)