package com.umc.ttoklip.data.model.stranger

data class OtherUserInfoResponse(
    val independentMonth: Int,
    val independentYear: Int,
    val nickname: String,
    val profileImage: String,
    val street: Any,
    val userId: Int
)