package com.umc.ttoklip.data.model.naver

data class GeocodingResponse(
    val status: String,
    val meta: Meta,
    val addresses: Addresses,
    val errorMessage: String
)
