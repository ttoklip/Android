package com.umc.ttoklip.data.model.mypage

data class ScrapListNewsResponse(
    val isFirst: Boolean,
    val isLast: Boolean,
    val newsletters: List<ScrapResponse>,
    val totalElements: Int,
    val totalPage: Int
)