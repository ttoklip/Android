package com.umc.ttoklip.data.model.honeytip

import java.io.Serializable

data class EditHoneyTip(
    val postId: Int,
    val title: String,
    val content: String,
    val category: String,
    val image: List<ImageUrl>,
    val url: String,
): Serializable
