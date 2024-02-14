package com.umc.ttoklip.data.model.town

import okhttp3.RequestBody

data class CreateTogethersRequest(
    val title: RequestBody,
    val content: RequestBody,
    val totalPrice: Long,
    val location: String,
    val chatUrl: String,
    val party: Long,
    val itemUrls: List<String>,
    val images: List<String>
)