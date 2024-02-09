package com.umc.ttoklip.data.model.search

data class NewsSearchResponse(
    val isFirst: Boolean,
    val isLast: Boolean,
    val newsletters: List<SearchResponse>,
    val totalElements: Int,
    val totalPage: Int
)