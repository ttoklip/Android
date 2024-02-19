package com.umc.ttoklip.data.model.home

import com.umc.ttoklip.data.model.honeytip.HoneyTipMain
import com.umc.ttoklip.data.model.news.News

data class HomeResponse(
    val currentMemberNickname: String,
    val honeyTips: List<HoneyTipMain>,
    val newsLetters: List<News>,
    val todayToDoList: String
){
    constructor() : this("", listOf(), listOf(),"")
}