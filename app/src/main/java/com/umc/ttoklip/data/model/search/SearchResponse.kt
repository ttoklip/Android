package com.umc.ttoklip.data.model.search

data class SearchResponse(
    val category: String,
    val commentCount: Int,
    val content: String,
    val id: Int,
    val title: String
){
    fun toModel(bigCategory: Int) = SearchModel(
        category, commentCount, content, id, title, bigCategory
    )
}