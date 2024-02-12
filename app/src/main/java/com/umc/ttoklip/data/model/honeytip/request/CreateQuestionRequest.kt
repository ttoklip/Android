package com.umc.ttoklip.data.model.honeytip.request

import com.google.gson.annotations.SerializedName

data class CreateQuestionRequest(
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("category")
    val category: String,
)
