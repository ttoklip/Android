package com.umc.ttoklip.presentation.honeytip.read

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.R
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.data.model.honeytip.CommentResponse
import com.umc.ttoklip.data.model.honeytip.InquireHoneyTipResponse
import com.umc.ttoklip.data.model.honeytip.InquireQuestionResponse
import com.umc.ttoklip.data.model.honeytip.request.HoneyTipCommentRequest
import com.umc.ttoklip.data.model.honeytip.request.ReportRequest
import com.umc.ttoklip.data.model.news.comment.NewsCommentResponse
import com.umc.ttoklip.data.model.question.QuestionCommentResponse
import com.umc.ttoklip.data.repository.honeytip.HoneyTipRepositoryImpl
import com.umc.ttoklip.module.onError
import com.umc.ttoklip.module.onException
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import com.umc.ttoklip.util.toReplyNicknameFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadHoneyTipViewModel @Inject constructor(
    private val repository: HoneyTipRepositoryImpl
) : ViewModel() {
    private val _honeyTip = MutableStateFlow<InquireHoneyTipResponse>(
        InquireHoneyTipResponse(
            0,
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,
            listOf(),
            listOf(),
            false,
            false,
            listOf(),
            ""
        )
    )
    val honeyTip = _honeyTip.asStateFlow()

    private val _readEvent = MutableSharedFlow<ReadEvent>()
    val readEvent = _readEvent.asSharedFlow()

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent: SharedFlow<String> get() = _toastEvent

    private val _comments = MutableStateFlow(listOf<CommentResponse>())
    val comments = _comments.asStateFlow()

    private val _questionComments = MutableStateFlow(listOf<QuestionCommentResponse>())
    val questionComments: StateFlow<List<QuestionCommentResponse>> get() = _questionComments

    val replyCommentParentId = MutableStateFlow(Pair(0, ""))
    val honeyTipCommentContent = MutableStateFlow("")
    val questionCommentContent = MutableStateFlow("")

    private val _isLike = MutableStateFlow(false)
    val isLike = _isLike.asStateFlow()

    private val _isCommentLike = MutableStateFlow(false)
    val isCommentLike = _isCommentLike.asStateFlow()

    private val _isScrap = MutableStateFlow(false)
    val isScrap = _isScrap.asStateFlow()

    private val _linkUrl = MutableStateFlow("")
    val linkUrl = _linkUrl.asStateFlow()


    sealed class ReadEvent {
        data class ReadHoneyTipEvent(val inquireHoneyTipResponse: InquireHoneyTipResponse) :
            ReadEvent()

        data class ReadQuestionEvent(val inquireQuestionResponse: InquireQuestionResponse) :
            ReadEvent()

        data class IncludeSwear(val message: String) : ReadEvent()
    }

    private fun eventRead(event: ReadEvent) {
        viewModelScope.launch {
            _readEvent.emit(event)
        }
    }

    fun inquireHoneyTip(honeyTipId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.inquireHoneyTip(honeyTipId).onSuccess {
                    _honeyTip.emit(it)
                    eventRead(ReadEvent.ReadHoneyTipEvent(_honeyTip.value))
                    _linkUrl.emit(it.urlResponses.firstOrNull()?.urls ?: "")
                    _isScrap.emit(it.scrapedByCurrentUser)
                    _isLike.emit(it.likedByCurrentUser)
                    _comments.emit(it.commentResponses.sortedBy { comment ->
                        comment.parentId ?: comment.commentId
                    })
                    Log.d("inquirehoneytip", it.toString())
                }.onError { Log.d("error", it.stackTraceToString()) }
                    .onFail {

                    }.onException { throw it }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("예외", "$e")
            }
        }
    }

    fun reportHoneyTip(honeyTipId: Int, request: ReportRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.reportHoneyTip(honeyTipId, request).onSuccess {
                Log.d("report HoneyTip", it.toString())
                _toastEvent.emit(TtoklipApplication.getString(R.string.report_post))
            }.onFail {
                _toastEvent.emit(TtoklipApplication.getString(R.string.report_post_fail))
            }
        }
    }

    fun deleteHoneyTip(honeyTipId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteHoneyTip(honeyTipId).onSuccess {
                Log.d("delete honeyTip", it.toString())
                _toastEvent.emit(TtoklipApplication.getString(R.string.delete_post))
            }
        }
    }

    fun postScrap() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.scrapHoneyTip(_honeyTip.value.honeyTipId).onSuccess {
                _isScrap.emit(true)
                _honeyTip.emit(honeyTip.value.copy().also {
                    it.scrapCount += 1
                })
                _toastEvent.emit(TtoklipApplication.getString(R.string.scrap))
            }
        }
    }

    fun deleteScrap() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteScrapHoneyTip(honeyTip.value.honeyTipId).onSuccess {
                _isScrap.emit(false)
                _honeyTip.emit(honeyTip.value.copy().also {
                    it.scrapCount -= 1
                })
                _toastEvent.emit(TtoklipApplication.getString(R.string.disscrap))
            }
        }
    }

    fun postLike() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.likeHoneyTip(honeyTip.value.honeyTipId).onSuccess {
                _isLike.emit(true)
                _honeyTip.emit(honeyTip.value.copy().also {
                    it.likeCount += 1
                })
                _toastEvent.emit(TtoklipApplication.getString(R.string.like))
            }
        }
    }

    fun deleteLike() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.likeHoneyTip(honeyTip.value.honeyTipId).onSuccess {
                _isLike.emit(false)
                _honeyTip.emit(honeyTip.value.copy().also {
                    it.likeCount -= 1
                })
                _toastEvent.emit(TtoklipApplication.getString(R.string.dislike))
            }
        }
    }

    fun postHoneyTipComment(postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val commentContent = honeyTipCommentContent.value.replace(
                replyCommentParentId.value.second,
                ""
            ).trim()
            if(commentContent.isEmpty()){
                _toastEvent.emit(TtoklipApplication.getString(R.string.blank_comment))
                return@launch
            }
            repository.postCommentHoneyTip(
                postId,
                HoneyTipCommentRequest(
                    commentContent, replyCommentParentId.value.first
                )
            ).onSuccess {
                inquireHoneyTip(postId)
            }.onError {
                it.printStackTrace()
            }.onFail { message ->
//                eventRead(ReadEvent.IncludeSwear(message))
                _toastEvent.emit(TtoklipApplication.getString(R.string.post_fail))
            }
        }
    }

    fun deleteHoneyTipComment(commentId: Int, postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCommentHoneyTip(commentId).onSuccess {
                inquireHoneyTip(postId)
                _toastEvent.emit(TtoklipApplication.getString(R.string.delete_comment))
            }
        }
    }

    fun postReportHoneyTipComment(commentId: Int, request: ReportRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.postReportCommentHoneyTip(commentId, request).onSuccess {
                _toastEvent.emit(TtoklipApplication.getString(R.string.report_comment))
            }.onFail {
                _toastEvent.emit(TtoklipApplication.getString(R.string.report_comment_fail))
            }
        }
    }

    fun postQuestionComment(postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val commentContent = questionCommentContent.value.replace(
                replyCommentParentId.value.second,
                ""
            ).trim()
            if(commentContent.isEmpty()){
                _toastEvent.emit(TtoklipApplication.getString(R.string.blank_comment))
                return@launch
            }

            repository.postCommentQuestion(
                postId,
                HoneyTipCommentRequest(
                    questionCommentContent.value,
                    replyCommentParentId.value.first
                )
            ).onSuccess {
                inquireQuestion(postId)
            }.onFail { message ->
//                eventRead(ReadEvent.IncludeSwear(message))
                _toastEvent.emit(TtoklipApplication.getString(R.string.post_fail))
            }
        }
    }

    fun deleteQuestionComment(commentId: Int, postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCommentQuestion(commentId).onSuccess {
                inquireQuestion(postId)
                _toastEvent.emit(TtoklipApplication.getString(R.string.delete_comment))
            }
        }
    }

    fun postReportQuestionComment(commentId: Int, request: ReportRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.postReportCommentQuestion(commentId, request).onSuccess {
                _toastEvent.emit(TtoklipApplication.getString(R.string.report_comment))
            }.onFail {
                _toastEvent.emit(TtoklipApplication.getString(R.string.report_comment_fail))
            }
        }
    }

    fun inquireQuestion(questionId: Int) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository.inquireQuestion(questionId).onSuccess {
                    eventRead(ReadEvent.ReadQuestionEvent(it))
                    _questionComments.emit(it.questionCommentResponses.sortedBy { comment ->
                        comment.parentId ?: comment.commentId
                    })
                    _isCommentLike.emit(it.likedByCurrentUser)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("예외", "$e")
        }
    }

    fun reportQuestion(questionId: Int, request: ReportRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.reportQuestion(questionId, request).onSuccess {
                Log.d("report Question", it.toString())
                _toastEvent.emit(TtoklipApplication.getString(R.string.report_post))
            }.onFail {
                _toastEvent.emit(TtoklipApplication.getString(R.string.report_post_fail))
            }
        }
    }

    fun likeQuestionComment(postId: Int, commentId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.postLikeAtQuestionComment(commentId).onSuccess {
                Log.d("it", it.toString())
                _isCommentLike.emit(true)
                inquireQuestion(postId)
                _toastEvent.emit(TtoklipApplication.getString(R.string.like_comment))
            }
        }
    }

    fun disLikeQuestionComment(postId: Int, commentId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteLikeAtQuestionComment(commentId).onSuccess {
                _isCommentLike.emit(false)
                inquireQuestion(postId)
                _toastEvent.emit(TtoklipApplication.getString(R.string.dislike))
            }
        }
    }
}