package com.umc.ttoklip.data.model.home

data class NotificationItem(
    val noticeSuccess: Boolean,
    val notificationId: Int,
    val targetClassId: Int,
    val targetClassName: String,
    val text: String,
    val title: String
)