package com.umc.ttoklip.data.model.naver.geocoding

data class GeocodingResponse(
    val status: String,
    val meta: Meta,
    val addresses: List<Addresses>,
    val errorMessage: String
)
