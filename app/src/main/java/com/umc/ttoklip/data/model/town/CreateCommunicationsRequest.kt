package com.umc.ttoklip.data.model.town

data class CreateCommunicationsRequest(
    val content: String,
    val images: List<String>,
    val title: String
)