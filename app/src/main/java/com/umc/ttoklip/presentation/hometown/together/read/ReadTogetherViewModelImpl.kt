package com.umc.ttoklip.presentation.hometown.together.read

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.model.town.CommentResponse
import com.umc.ttoklip.data.model.town.CreateCommentRequest
import com.umc.ttoklip.data.model.town.PatchCartStatusRequest
import com.umc.ttoklip.data.model.town.ReportRequest
import com.umc.ttoklip.data.model.town.ViewTogetherResponse
import com.umc.ttoklip.data.repository.town.ReadTogetherRepository
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
class ReadTogetherViewModelImpl @Inject constructor(private val repository: ReadTogetherRepository) :
    ViewModel(), ReadTogetherViewModel {
    private val _joinState = MutableStateFlow<Boolean>(true)
    override val joinState: StateFlow<Boolean>
        get() = _joinState

    private val _deadlineState = MutableStateFlow<Boolean>(true)
    override val deadlineState: StateFlow<Boolean>
        get() = _deadlineState

    private val _isWriter = MutableStateFlow<Boolean>(false)
    override val isWriter: StateFlow<Boolean>
        get() = _isWriter

    private val _postId: MutableStateFlow<Long> = MutableStateFlow(0L)
    override val postId: StateFlow<Long>
        get() = _postId

    private val _writer = MutableStateFlow("")
    override val writer: StateFlow<String>
        get() = _writer

    private val _toast = MutableSharedFlow<String>()
    override val toast: SharedFlow<String>
        get() = _toast

    private val _comments = MutableStateFlow(listOf<CommentResponse>())
    override val comments: StateFlow<List<CommentResponse>>
        get() = _comments

    override val replyCommentParentId = MutableStateFlow(0)
    override val commentContent = MutableStateFlow("")

    private val _postContent: MutableStateFlow<ViewTogetherResponse> =
        MutableStateFlow<ViewTogetherResponse>(
            ViewTogetherResponse(
                "",
                0,
                "",
                emptyList(),
                "",
                "",
                0L,
                0L,
                0L,
                "",
                "",
                0L,
                emptyList(),
                "",
                false
            )
        )
    override val postContent: StateFlow<ViewTogetherResponse>
        get() = _postContent

    override fun joinBtnClick() {
        viewModelScope.launch {
            if (joinState.value) {
                joinTogether()
            } else {
                cancelTogether()
            }
            //_joinState.emit(_joinState.value.not())

        }
    }

    override fun setJoinState(joinState: Boolean) {
        _joinState.value = joinState
    }

    override fun savePostId(postId: Long) {
        _postId.value = postId
    }

    override fun checkWriter(writer: String) {
        Log.d("local wrter", writer)
        Log.d("writer", _writer.value)
        _isWriter.value = writer.equals(_writer.value)
        Log.d("isWrier", _isWriter.value.toString())
    }


    override fun readTogether(postId: Long) {
        viewModelScope.launch {
            repository.viewTogether(postId).onSuccess {
                _postContent.value = it
                _deadlineState.value = it.status != "IN_PROGRESS"
                Log.d("_deadline", _deadlineState.value.toString())
                _joinState.value = !it.alreadyJoin
                _writer.value = it.writer
                _comments.value = it.commentResponses
                Log.d("response writer", _writer.value)
            }.onError {

            }
        }
    }

    override fun reportPost(reportRequest: ReportRequest) {
        viewModelScope.launch {
            if (postId.value != 0L) {
                repository.reportTogether(postId.value, reportRequest).onSuccess {
                    _toast.emit("게시글 신고가 완료되었습니다.")
                }.onFail {
                    _toast.emit("게시글 신고 타입을 설정해주세요.")
                }
            }
        }
    }

    override fun reportComment(
        commentId: Long,
        reportRequest: ReportRequest
    ) {
        viewModelScope.launch {
            repository.reportTogetherComment(commentId, reportRequest).onSuccess {
                _toast.emit("댓글 신고가 완료되었습니다.")
            }.onError {
                Log.d("error", it.toString())
            }.onFail {
                _toast.emit("댓글 신고 타입을 설정해주세요.")
            }
        }
    }

    override fun deleteComment(commentId: Long) {
        viewModelScope.launch {
            repository.deleteTogetherComment(commentId).onSuccess {
                readTogether(postId.value)
            }
        }
    }

    override fun createComment() {
        viewModelScope.launch {
            if (postId.value != 0L) {
                repository.createTogetherComment(
                    postId.value,
                    CreateCommentRequest(commentContent.value, replyCommentParentId.value.toLong())
                ).onSuccess {
                    readTogether(postId.value)
                }
            }
        }
    }

    override fun joinTogether() {
        viewModelScope.launch {
            if (postId.value != 0L) {
                repository.joinTogether(postId.value).onSuccess {
                    //_joinState.value = true
                    readTogether(postId.value)
                    Log.d("join Together", it.toString())
                }
            }
        }
    }

    override fun cancelTogether() {
        viewModelScope.launch {
            if (postId.value != 0L) {
                repository.cancelTogether(postId.value).onSuccess {
                    //_joinState.value = false
                    readTogether(postId.value)
                }
            }
        }
    }

    override fun fetchParticipantsCount() {
        viewModelScope.launch {
            if (postId.value != 0L){
                repository.fetchParticipantsCount(postId.value).onSuccess {
                    Log.d("fetchParticipantsCount", it.toString())
                }
            }
        }
    }

    override fun patchPostStatus(status: String) {
        viewModelScope.launch{
            if (postId.value != 0L){
                repository.patchPostStatus(postId.value, PatchCartStatusRequest(status)).onSuccess {
                    _deadlineState.value = true
                    readTogether(postId.value)
                    Log.d("patchPostStatus", it.toString())
                }
            }
        }
    }
}