package com.umc.ttoklip.data.model.signup

data class SignupLocalRequest(
    val email:String,
    val password:String,
    val originName:String,
    val nickname:String,
    val independentYear:Int,
    val independentMonth:Int,
    val street:String,
)
