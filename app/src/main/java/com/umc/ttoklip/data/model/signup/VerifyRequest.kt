package com.umc.ttoklip.data.model.signup

data class VerifyRequest(
    val email:String,
    val verifyCode:String
)
