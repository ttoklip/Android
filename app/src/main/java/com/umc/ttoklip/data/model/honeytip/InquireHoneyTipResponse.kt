package com.umc.ttoklip.data.model.honeytip

import java.io.Serializable

data class InquireHoneyTipResponse(
    val honeyTipId: Int,
    val title: String,
    val content: String,
    val writer: String?,
    val writtenTime: String,
    val category: String,
    val imageUrls: List<String>,
    val commentResponse: List<CommentResponse>,
    val urlResponses: List<String>
): Serializable
