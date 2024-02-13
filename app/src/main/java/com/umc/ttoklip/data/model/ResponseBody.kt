package com.umc.ttoklip.data.model

data class ResponseBody<T>(
    val time: String,
    val status: Int,
    val code: String,
    val message: String,
    val result: T
)