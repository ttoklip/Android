package com.umc.ttoklip.presentation.hometown.communication.read

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.model.town.CommentResponse
import com.umc.ttoklip.data.model.town.CreateCommentRequest
import com.umc.ttoklip.data.model.town.ReportRequest
import com.umc.ttoklip.data.model.town.ViewCommunicationResponse
import com.umc.ttoklip.data.repository.town.ReadCommsRepository
import com.umc.ttoklip.module.onError
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadCommunicationViewModelImpl @Inject constructor(
    private val repository: ReadCommsRepository
) : ViewModel(),
    ReadCommunicationViewModel {
    private val _postContent: MutableStateFlow<ViewCommunicationResponse> =
        MutableStateFlow<ViewCommunicationResponse>(
            ViewCommunicationResponse(
                "",
                0,
                "",
                emptyList(),
                "",
                "",
                0,
                0,
                0,
                listOf(CommentResponse(0L, "", 0, "", "")),
                false,
                false
            )
        )

    private val _postId: MutableStateFlow<Long> = MutableStateFlow(0L)
    override val postId: StateFlow<Long>
        get() = _postId

    override val postContent: StateFlow<ViewCommunicationResponse>
        get() = _postContent

    private val _like: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val like: StateFlow<Boolean>
        get() = _like

    private val _scrap: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val scrap: StateFlow<Boolean>
        get() = _scrap

    override val replyCommentParentId = MutableStateFlow(0)
    override val commentContent = MutableStateFlow("")

    private val _comments = MutableStateFlow(listOf<CommentResponse>())
    override val comments: StateFlow<List<CommentResponse>>
        get() = _comments

    private val _toast = MutableSharedFlow<String>()
    override val toast: SharedFlow<String>
        get() = _toast

    private val _toastEvent = MutableSharedFlow<ReadCommunicationViewModel.ToastEvent>()
    override val toastEvent: SharedFlow<ReadCommunicationViewModel.ToastEvent>
        get() = _toastEvent

    override fun eventToast(event: ReadCommunicationViewModel.ToastEvent) {
        viewModelScope.launch {
            _toastEvent.emit(event)
        }
    }

    override fun savePostId(postId: Long) {
        _postId.value = postId
    }

    override fun readCommunication(postId: Long) {
        viewModelScope.launch {
            repository.viewComms(postId).onSuccess {
                _postContent.value = it
                _like.value = it.likedByCurrentUser
                _scrap.value = it.scrapedByCurrentUser
                _comments.value = it.commentResponses.sortedBy { comment ->
                    comment.parentId ?: comment.commentId
                }
            }.onError {

            }
        }
    }

    override fun deleteCommunication() {
        viewModelScope.launch {
            repository.deleteComms(_postId.value).onSuccess {
                _toast.emit("게시글 삭제가 완료되었습니다.")
            }
        }
    }

    override fun changeScrap() {
        Log.d("change", "스크랩")
        _scrap.value = _scrap.value.not()
        Log.d("scrap", scrap.value.toString())
        viewModelScope.launch {
            if (_scrap.value) {
                repository.addCommsScrap(postId.value).onSuccess {
                    _postContent.emit(_postContent.value.copy().also {
                        it.scrapCount += 1
                    })
                }
            } else {
                repository.cancelCommsScrap(postId.value).onSuccess {
                    Log.d("change", "스크랩")
                    _postContent.emit(_postContent.value.copy().also {
                        it.scrapCount -= 1
                    })
                }
            }
        }
    }

    override fun changeLike() {
        Log.d("change", "좋아요")
        _like.value = _like.value.not()
        viewModelScope.launch {
            if (_like.value) {
                repository.addCommsLike(postId.value).onSuccess {
                    _postContent.emit(_postContent.value.copy().also {
                        it.likeCount += 1
                    })
                }
            } else {
                repository.cancelCommsLike(postId.value).onSuccess {
                    _postContent.emit(_postContent.value.copy().also {
                        it.likeCount -= 1
                    })
                }
            }
        }
    }

    override fun reportPost(reportRequest: ReportRequest) {
        viewModelScope.launch {
            if (postId.value != 0L) {
                repository.reportComms(postId.value, reportRequest).onSuccess {
                    Log.d("report", it.toString())
                    _toast.emit("게시글 신고가 완료되었습니다.")
                }.onFail {
                    _toast.emit("게시글 신고 타입을 설정해주세요.")
                }
            }
        }
    }

    override fun reportComment(
        commentId: Long,
        reportRequest: com.umc.ttoklip.data.model.honeytip.request.ReportRequest
    ) {
        viewModelScope.launch {
            repository.reportCommsComment(commentId, reportRequest).onSuccess {
                _toast.emit("댓글 신고가 완료되었습니다.")
            }.onFail {
                _toast.emit("댓글 신고 타입을 설정해주세요.")
            }
        }
    }

    override fun deleteComment(commentId: Long) {
        viewModelScope.launch {
            repository.deleteCommsComment(commentId).onSuccess {
                readCommunication(postId.value)
            }
        }
    }

    override fun createComment() {
        viewModelScope.launch {
            if (postId.value != 0L) {
                repository.createCommsComment(
                    postId.value,
                    CreateCommentRequest(commentContent.value, replyCommentParentId.value.toLong())
                ).onSuccess {
                    readCommunication(postId.value)
                }
            }
        }
    }

}