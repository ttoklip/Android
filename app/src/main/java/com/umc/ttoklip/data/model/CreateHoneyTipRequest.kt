package com.umc.ttoklip.data.model

data class CreateHoneyTipRequest(
    val title: String,
    val content: String,
    val category: String,
    val url: List<String>
)
