package com.umc.ttoklip.data.model.news

data class NewsPageResponse(
    val isFirst: Boolean,
    val isLast: Boolean,
    val newsletterThumbnailRespons: List<News>,
    val totalElements: Int,
    val totalPage: Int
)