package com.umc.ttoklip.data.model

data class CreateHoneyTipRequest(
    val title: String,
    val content: String,
    val images: List<String>,
    val url: List<String>
)
