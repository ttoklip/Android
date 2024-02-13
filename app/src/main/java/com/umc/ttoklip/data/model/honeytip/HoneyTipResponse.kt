package com.umc.ttoklip.data.model.honeytip

data class HoneyTipResponse(
    val honeyTipId: Int,
    val title: String,
    val content: String,
    val writer: String?,
    val likeCount: Int,
    val commentCount: Int,
    val writtenTime: String
)
