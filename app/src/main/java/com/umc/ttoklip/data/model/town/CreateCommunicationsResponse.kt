package com.umc.ttoklip.data.model.town

data class CreateCommunicationsResponse(
    val author: String,
    val communityId: Long,
    val content: String,
    val title: String,
    val writtenTime: String
)