package com.umc.ttoklip.data.model.search

data class SearchModel(
    val category: String,
    val commentCount: Int,
    val content: String,
    val id: Int,
    val title: String,
    val bigCategory : Int
)
