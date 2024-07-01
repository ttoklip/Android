package com.umc.ttoklip.data.model.mypage

data class MyBlockUserResponse(
    val blockedUsers: List<BlockedUser>
){
    constructor(): this(emptyList())
}