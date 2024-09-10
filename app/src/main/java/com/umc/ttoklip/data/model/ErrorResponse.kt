package com.umc.ttoklip.data.model

data class ErrorResponse(
    val status: Int,
    val errorCode: String,
    var message: String
)
