package com.umc.ttoklip.data.model.news

data class MainNewsResponse(
    val categoryResponses: CategoryResponses,
    val randomNews: List<Any>
)