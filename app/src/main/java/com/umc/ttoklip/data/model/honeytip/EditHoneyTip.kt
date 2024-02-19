package com.umc.ttoklip.data.model.honeytip

import java.io.Serializable

data class EditHoneyTip(
    val postId: Int,
    val title: String,
    val content: String,
    val category: String,
    val image: Array<String>,
    val url: String,
): Serializable
