package com.umc.ttoklip.data.model.news

data class MainNewsResponse(
    val newsletterThumbnailResponse: CategoryResponses,
    val randomNews: List<Any>
)