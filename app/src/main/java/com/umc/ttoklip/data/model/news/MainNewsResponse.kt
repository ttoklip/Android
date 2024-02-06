package com.umc.ttoklip.data.model.news

data class MainNewsResponse(
    val categoryResponse: CategoryResponse,
    val topFiveQuestions: List<TopFiveQuestion>
)