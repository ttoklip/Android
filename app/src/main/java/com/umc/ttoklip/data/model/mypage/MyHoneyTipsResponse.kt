package com.umc.ttoklip.data.model.mypage

data class MyHoneyTipsResponse(
    val honeyTips: List<HoneyTip>,
    val isFirst: Boolean,
    val isLast: Boolean,
    val totalElements: Int,
    val totalPage: Int
)