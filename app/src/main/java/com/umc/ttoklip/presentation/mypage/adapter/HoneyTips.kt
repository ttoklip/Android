package com.umc.ttoklip.presentation.mypage.adapter

data class HoneyTip(
    val writer: String,
    val title: String,
    val body: String,
    val date: String,
    val chatCnt: Int = 0,
)