package com.umc.ttoklip.data.model.news.detail

import com.umc.ttoklip.data.model.news.comment.NewsCommentResponse

data class NewsDetailResponse(
    val category: String,
    val commentResponses: List<NewsCommentResponse>,
    val content: String,
    val imageUrlList: List<ImageUrl>,
    val newsletterId: Int,
    var likeCount :Int,
    var scrapCount : Int,
    val title: String?,
    val urlList: List<Url>,
    val writer: String?,
    val writtenTime: String
){
    constructor():this("", listOf(),"", listOf(),0,0,0,"", listOf(),"","")
}