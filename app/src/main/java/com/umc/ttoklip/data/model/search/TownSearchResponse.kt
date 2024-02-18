package com.umc.ttoklip.data.model.search

data class TownSearchResponse(
    val isFirst: Boolean,
    val isLast: Boolean,
    val communities: List<SearchNoResponse>,
    val totalElements: Int,
    val totalPage: Int
)