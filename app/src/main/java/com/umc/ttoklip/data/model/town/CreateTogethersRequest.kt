package com.umc.ttoklip.data.model.town

import okhttp3.MultipartBody

data class CreateTogethersRequest(
    val title: String,
    val content: String,
    val totalPrice: Long,
    val location: String,
    val chatUrl: String,
    val party: Long,
    val itemUrls: List<String>,
    val images: List<MultipartBody.Part>
)