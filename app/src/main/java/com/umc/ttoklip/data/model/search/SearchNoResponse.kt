package com.umc.ttoklip.data.model.search

data class SearchNoResponse(
    val category: String,
    val commentCount: Int,
    val content: String,
    val id: Int,
    val likeCount: Int,
    val scrapCount: Int,
    val title: String,
    val writer: String?
) {
    fun toModel(category: String, bigCategory: Int) = SearchModel(
        category, commentCount, content, id, title, bigCategory , likeCount, scrapCount , writer ?: ""
    )
}