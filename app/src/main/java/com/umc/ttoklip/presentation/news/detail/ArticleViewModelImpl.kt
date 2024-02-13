package com.umc.ttoklip.presentation.news.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.model.news.ReportRequest
import com.umc.ttoklip.data.model.news.comment.NewsCommentRequest
import com.umc.ttoklip.data.model.news.comment.NewsCommentResponse
import com.umc.ttoklip.data.model.news.detail.ImageUrl
import com.umc.ttoklip.data.model.news.detail.NewsDetailResponse
import com.umc.ttoklip.data.repository.news.NewsRepository
import com.umc.ttoklip.module.onException
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModelImpl @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel(), ArticleViewModel {

    private val _newsDetail = MutableStateFlow(NewsDetailResponse())
    override val newsDetail: StateFlow<NewsDetailResponse>
        get() = _newsDetail

    private val _comments = MutableStateFlow(listOf<NewsCommentResponse>())
    override val comments: StateFlow<List<NewsCommentResponse>>
        get() = _comments

    private val _imageUrls = MutableStateFlow(listOf<ImageUrl>())
    override val imageUrls: StateFlow<List<ImageUrl>>
        get() = _imageUrls

    override val replyCommentParentId = MutableStateFlow(0)
    override val commentContent = MutableStateFlow("")

    override fun getDetail(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                newsRepository.getDetailNews(id)
                    .onSuccess {
                        _newsDetail.emit(it)
                        _comments.emit(it.commentResponses.sortedBy { comment ->
                            comment.parentId ?: comment.commentId
                        })
                        _imageUrls.emit(it.imageUrlList)
                    }.onFail {

                    }.onException {
                        throw it
                    }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("예외", "$e")
            }
        }
    }

    override fun postComment(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                newsRepository.postCommentNews(
                    id,
                    NewsCommentRequest(commentContent.value, replyCommentParentId.value)
                ).onSuccess {
                    getDetail(id)
                }.onFail {

                }.onException {
                    throw it
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("예외", "$e")
            }
        }
    }


    override fun deleteComment(id: Int,postId :Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                newsRepository.deleteCommentNews(
                    id
                ).onSuccess {
                    getDetail(postId)
                }.onFail {

                }.onException {
                    throw it
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("예외", "$e")
            }
        }
    }
    override fun postReportNews(id: Int, content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                newsRepository.postReportNews(
                    id,
                    ReportRequest(content, reportType = "INAPPROPRIATE_CONTENT")
                ).onSuccess {

                }.onFail {

                }.onException {
                    throw it
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("예외", "$e")
            }
        }
    }

    override fun postReportComment(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                newsRepository.postReportCommentNews(
                    id,
                    ReportRequest("부적절한 댓글", reportType = "INAPPROPRIATE_CONTENT")
                ).onSuccess {

                }.onFail {

                }.onException {
                    throw it
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("예외", "$e")
            }
        }
    }


}