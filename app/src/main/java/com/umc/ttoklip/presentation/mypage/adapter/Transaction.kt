package com.umc.ttoklip.presentation.mypage.adapter

data class Transaction(
    val title: String,
    val date: String,
    val icon: String? = null,
    val ownerId: String,
    val address: String,
    val currentAmount: Int,
    val targetAmount: Int,
    val currentMember: Int,
    val targetMember: Int,
    val commentAmount: Int,
    val closureReason: String?
)