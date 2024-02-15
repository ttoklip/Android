package com.umc.ttoklip.presentation.news.detail

import com.umc.ttoklip.data.model.news.comment.NewsCommentResponse
import com.umc.ttoklip.data.model.news.detail.ImageUrl
import com.umc.ttoklip.data.model.news.detail.NewsDetailResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface ArticleViewModel {


    val newsDetail: StateFlow<NewsDetailResponse>
    val comments: StateFlow<List<NewsCommentResponse>>
    val imageUrls: StateFlow<List<ImageUrl>>
    val replyCommentParentId :MutableStateFlow<Int>
    val commentContent: MutableStateFlow<String>
    val toast:MutableStateFlow<String>
    val isLike :MutableStateFlow<Boolean>
    val isScrap :MutableStateFlow<Boolean>

    fun getDetail(id: Int)
    fun postComment(id: Int)
    fun postReportNews(id : Int, content : String)
    fun postReportComment(id : Int)
    fun deleteComment(id: Int,postId :Int)
    fun postLike()
    fun deleteLike()
    fun postScrap()
    fun deleteScrap()
}