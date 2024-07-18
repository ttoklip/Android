package com.umc.ttoklip.data.model.town

import java.io.Serializable

data class EditCommunication(
    val postId: Long,
    val title: String,
    val content: String,
): Serializable