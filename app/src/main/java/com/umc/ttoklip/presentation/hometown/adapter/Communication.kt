package com.umc.ttoklip.presentation.hometown.adapter

data class Communication(
    val writer: String,
    val title: String,
    val body: String,
    val date: String,
    val chatCnt: Int = 0,
)