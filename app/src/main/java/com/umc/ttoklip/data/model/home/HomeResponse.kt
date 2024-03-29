package com.umc.ttoklip.data.model.home

import com.umc.ttoklip.data.model.honeytip.HoneyTipMain
import com.umc.ttoklip.data.model.news.News
import com.umc.ttoklip.data.model.town.Togethers
import com.umc.ttoklip.presentation.hometown.adapter.Together

data class HomeResponse(
    val currentMemberNickname: String,
    val honeyTips: List<HoneyTipMain>,
    val newsLetters: List<News>,
    val carts: List<Togethers>,
    val todayToDoList: String,
    val street : String
){
    constructor() : this("", listOf(), listOf(), listOf(),"", "")
}