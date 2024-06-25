package com.umc.ttoklip.data.model.mypage

data class NoticeDetail(
    val noticeId:Int,
    val title:String,
    val content:String,
    var visibility: Boolean=false,
)
