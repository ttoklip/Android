package com.umc.ttoklip.data.model

import com.google.gson.annotations.SerializedName

data class CreateHoneyTipRequest(
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("url")
    val url: List<String>
)
