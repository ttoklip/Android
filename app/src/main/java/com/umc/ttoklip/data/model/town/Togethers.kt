package com.umc.ttoklip.data.model.town

data class Togethers(
    val id: Long,
    val title: String,
    val location: String,
    val totalPrice: Long,
    val partyMax: Long,
    val writer: String,
    val partyCnt: Long,
    val commentCount: Long,
    val currentPrice: Long,
    val tradeStatus: String
)
