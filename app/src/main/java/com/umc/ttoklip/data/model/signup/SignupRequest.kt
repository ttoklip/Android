package com.umc.ttoklip.data.model.signup

data class SignupRequest(
    val street:String,
    val nickname:String,
    val category:ArrayList<String>,
    val profileImage:String,
    val independentYear:Int,
    val independentMonth:Int
)
