package com.umc.ttoklip.presentation.news.adapter

import com.umc.ttoklip.data.model.news.News

data class NewsCard(
    val category: String,
    val newsList : List<News>
)
