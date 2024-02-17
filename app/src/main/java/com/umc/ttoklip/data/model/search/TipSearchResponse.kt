package com.umc.ttoklip.data.model.search

data class TipSearchResponse(
    val isFirst: Boolean,
    val isLast: Boolean,
    val honeyTips: List<SearchResponse>,
    val totalElements: Int,
    val totalPage: Int
)