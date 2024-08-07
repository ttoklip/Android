package com.umc.ttoklip.data.model.town

import com.umc.ttoklip.data.model.honeytip.ImageUrl
import java.io.Serializable

data class EditCommunication(
    val postId: Long,
    val title: String,
    val content: String,
    val image: List<com.umc.ttoklip.data.model.town.ImageUrl>,
): Serializable