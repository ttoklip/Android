package com.umc.ttoklip.data.model.mypage

data class ScrapListTipResponse(
    val honeyTips: List<ScrapResponse>,
    val isFirst: Boolean,
    val isLast: Boolean,
    val totalElements: Int,
    val totalPage: Int
)