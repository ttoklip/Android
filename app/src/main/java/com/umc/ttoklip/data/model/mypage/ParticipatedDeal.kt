package com.umc.ttoklip.data.model.mypage

data class ParticipatedDeal(
    val comments: Int,
    val dealId: Long,
    val description: String,
    val participantsCount: Long,
    val participationTime: String,
    val price: Long,
    val seller: String,
    val status: String,
    val title: String
)