package com.umc.ttoklip.data.model.mypage

data class MyPageInfoRequest(
    var street: String?,
    var nickname: String,
    var categories: List<String>,
    var profileImage: String?,
    var independentYear: Int,
    var independentMonth: Int
)
