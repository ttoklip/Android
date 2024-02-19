package com.umc.ttoklip.data.model.mypage

data class MyQuestionResponse(
    val communities: List<Community>,
    val isFirst: Boolean,
    val isLast: Boolean,
    val totalElements: Int,
    val totalPage: Int
)