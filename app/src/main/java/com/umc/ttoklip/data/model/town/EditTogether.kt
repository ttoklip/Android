package com.umc.ttoklip.data.model.town

import com.umc.ttoklip.data.model.honeytip.ImageUrl
import com.umc.ttoklip.presentation.honeytip.adapter.Image
import java.io.Serializable

data class EditTogether(
    val postId: Long,
    val title: String,
    val content: String,
    val price: String,
    val member: Int,
    val address: String,
    val image: List<ImageUrl>,
    val url: String,
): Serializable