package com.umc.ttoklip.presentation.news.detail

import com.umc.ttoklip.data.model.news.comment.NewsCommentResponse
import com.umc.ttoklip.data.model.news.detail.ImageUrl
import com.umc.ttoklip.data.model.news.detail.NewsDetailResponse
import kotlinx.coroutines.flow.StateFlow

interface ArticleViewModel {


    val newsDetail : StateFlow<NewsDetailResponse>
    val comments: StateFlow<List<NewsCommentResponse>>
    val imageUrls : StateFlow<List<ImageUrl>>

    fun getDetail(id : Int)
}