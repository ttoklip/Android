package com.umc.ttoklip.data.model.home

data class CartResponse(
    val commentCount: Int,
    val currentPrice: Int,
    val id: Int,
    val location: String,
    val partyCnt: Int,
    val partyMax: Int,
    val title: String,
    val totalPrice: Int,
    val tradeStatus: String,
    val writer: String
)