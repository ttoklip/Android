package com.umc.ttoklip.data.model.honeytip

import java.io.Serializable

data class Question(
    var title: String,
    var content: String,
    val images: Array<String>?,
): Serializable
