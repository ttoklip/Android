package com.umc.ttoklip.data.model.town

import com.umc.ttoklip.presentation.hometown.adapter.Together

data class CartRecent3(
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
){
    fun toModel()= Together(title,location,id,"함께해요")
}