package com.umc.ttoklip.data.model.news

data class WelfarePolicyQuestion(
    val category: String,
    val commentCount: Int,
    val content: String,
    val questionId: Int,
    val title: String,
    val writer: String,
    val writtenTime: String
)