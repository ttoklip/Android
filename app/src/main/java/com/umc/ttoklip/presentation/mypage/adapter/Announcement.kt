package com.umc.ttoklip.presentation.mypage.adapter

data class Announcement(
    val date: String,
    val title: String,
    val announcement: List<AnnouncementContent>,
    var visibility: Boolean = false
)