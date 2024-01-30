package com.umc.ttoklip.presentation.news.adapter

data class Comment(
    val id : Int,
    val isReply : Int,
    val comment : String,
    val user : String
)
