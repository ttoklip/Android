package com.umc.ttoklip.data.model.mypage

data class ScrapListTownResponse(
    val isFirst: Boolean,
    val isLast: Boolean,
    val communities: List<ScrapResponse>,
    val totalElements: Int,
    val totalPage: Int
)