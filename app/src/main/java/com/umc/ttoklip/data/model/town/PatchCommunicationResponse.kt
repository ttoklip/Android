package com.umc.ttoklip.data.model.town

data class PatchCommunicationResponse(
    val author: String,
    val communityId: Long,
    val content: String,
    val title: String,
    val writtenTime: String
)