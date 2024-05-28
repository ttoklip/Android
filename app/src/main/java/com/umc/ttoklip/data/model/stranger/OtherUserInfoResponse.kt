package com.umc.ttoklip.data.model.stranger

data class OtherUserInfoResponse(
    val independentMonth: Int,
    val independentYear: Int,
    val nickname: String,
    val profileImage: String,
    val street: String?,
    val userId: Int
) {
    constructor() : this(0, 0, "", "", "", 0)
}