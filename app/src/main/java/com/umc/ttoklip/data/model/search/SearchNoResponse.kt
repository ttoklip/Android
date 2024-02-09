package com.umc.ttoklip.data.model.search

data class SearchNoResponse(
    val commentCount: Int,
    val content: String,
    val id: Int,
    val title: String
) {
    fun toModel(category: String, bigCategory: Int) = SearchModel(
        category, commentCount, content, id, title, bigCategory
    )
}