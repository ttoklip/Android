package com.umc.ttoklip.data.model.honeytip.request

import android.net.Uri
import com.google.gson.annotations.SerializedName

data class CreateHoneyTipRequest(
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("category")
    val category: String,
    val images: List<Uri>,
    @SerializedName("url")
    val url: List<String>
)
