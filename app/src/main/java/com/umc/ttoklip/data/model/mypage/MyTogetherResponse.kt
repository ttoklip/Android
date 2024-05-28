package com.umc.ttoklip.data.model.mypage

import com.umc.ttoklip.data.model.town.Togethers

data class MyTogetherResponse(
    val carts: List<Togethers>,
    val totalPage: Int,
    val totalElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean,
)