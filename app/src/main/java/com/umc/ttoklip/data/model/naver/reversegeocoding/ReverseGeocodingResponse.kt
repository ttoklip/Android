package com.umc.ttoklip.data.model.naver.reversegeocoding

data class ReverseGeocodingResponse(
    val status: StatusResult,
    val results: List<ReverseGeoCodingResult>
)
