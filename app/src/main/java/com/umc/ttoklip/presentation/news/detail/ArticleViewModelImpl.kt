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

    private val  _toast = MutableStateFlow("")
    override val toast: MutableStateFlow<String>
        get() = _toast

    private val _isLike = MutableStateFlow(false)
    override val isLike: MutableStateFlow<Boolean>
        get() = _isLike

    private val _isScrap = MutableStateFlow(false)
    override val isScrap: MutableStateFlow<Boolean>
        get() = _isScrap

    override fun getDetail(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                newsRepository.getDetailNews(id)
                    .onSuccess {
                        _newsDetail.emit(it)
                        //라이크 스크랩 상태 받을 예정

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
                    _toast.emit("댓글을 삭제했습니다.")
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

    override fun postLike() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                newsRepository.postLikeNews(
                    newsDetail.value.newsletterId
                ).onSuccess {
                    _isLike.emit(true)
                    _newsDetail.emit(newsDetail.value.copy().also {
                        it.likeCount += 1
                    })
                    _toast.emit("뉴스 좋아요")
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

    override fun deleteLike() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                newsRepository.deleteLikeNews(
                    newsDetail.value.newsletterId
                ).onSuccess {
                    _isLike.emit(false)
                    _newsDetail.emit(newsDetail.value.copy().also {
                        it.likeCount -= 1
                    })
                    _toast.emit("뉴스 좋아요 취소")
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

    override fun postScrap() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                newsRepository.postScrapNews(
                    newsDetail.value.newsletterId
                ).onSuccess {
                    _isScrap.emit(true)
                    _newsDetail.emit(newsDetail.value.copy().also {
                        it.scrapCount += 1
                    })
                    _toast.emit("뉴스 스크랩")
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

    override fun deleteScrap() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                newsRepository.deleteScrapNews(
                    newsDetail.value.newsletterId
                ).onSuccess {
                    _isScrap.emit(false)
                    _newsDetail.emit(newsDetail.value.copy().also {
                        it.scrapCount -= 1
                    })
                    _toast.emit("뉴스 스크랩 취소")
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
                    _toast.emit("게시글을 신고했습니다.")
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
                    _toast.emit("댓글을 신고했습니다.")
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