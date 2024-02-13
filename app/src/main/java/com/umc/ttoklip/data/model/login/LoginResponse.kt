package com.umc.ttoklip.data.model.login

data class LoginResponse (
    val jwtToken:String,
    val ifFirstLogin:Boolean
)