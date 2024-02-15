package com.umc.ttoklip.data.model.town

import okhttp3.MultipartBody

data class CreateCommunicationsRequest(
    val content: String,
    val images: List<MultipartBody.Part>,
    val title: String
)