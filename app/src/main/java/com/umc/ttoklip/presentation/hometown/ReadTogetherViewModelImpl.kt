package com.umc.ttoklip.presentation.hometown

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.model.town.CreateCommentRequest
import com.umc.ttoklip.data.model.town.ReportRequest
import com.umc.ttoklip.data.model.town.ViewTogetherResponse
import com.umc.ttoklip.data.repository.town.ReadTogetherRepository
import com.umc.ttoklip.module.onError
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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

    private val _isOwner = MutableStateFlow<Boolean>(false)
    override val isOwner: StateFlow<Boolean>
        get() = _isOwner

    private val _postId: MutableStateFlow<Long> = MutableStateFlow(0L)
    override val postId: StateFlow<Long>
        get() = _postId

    private val _postContent: MutableStateFlow<ViewTogetherResponse> =
        MutableStateFlow<ViewTogetherResponse>(
            ViewTogetherResponse("", 0, "", emptyList(), "", "", 0L, 0L, 0L, 0L, emptyList(), "")
        )
    override val postContent: StateFlow<ViewTogetherResponse>
        get() = _postContent

    override fun joinBtnClick() {
        viewModelScope.launch {
            _joinState.emit(_joinState.value.not())
        }
    }

    override fun savePostId(postId: Long) {
        _postId.value = postId
    }

    override fun readTogether(postId: Long) {
        viewModelScope.launch {
            repository.viewTogether(postId).onSuccess {
                _postContent.value = it
                _deadlineState.value = it.status != "IN_PROGRESS"
            }.onError {

            }
        }
    }

    override fun reportPost(reportRequest: ReportRequest) {
        viewModelScope.launch {
            if (postId.value != 0L) {
                repository.reportTogether(postId.value, reportRequest)
            }
        }
    }

    override fun reportComment(commentId: Long, reportRequest: ReportRequest) {
        viewModelScope.launch {
            repository.reportTogetherComment(commentId, reportRequest)
        }
    }

    override fun deleteComment(commentId: Long) {
        viewModelScope.launch {
            repository.deleteTogetherComment(commentId)
        }
    }

    override fun createComment(body: CreateCommentRequest) {
        viewModelScope.launch {
            if (postId.value != 0L) {
                repository.createTogetherComment(postId.value, body)
            }
        }
    }

    override fun joinTogether() {
        viewModelScope.launch {
            if (postId.value != 0L) {
                repository.joinTogether(postId.value).onSuccess {
                    _joinState.value = true
                }
            }
        }
    }

    override fun cancelTogether() {
        viewModelScope.launch {
            if (postId.value != 0L) {
                repository.joinTogether(postId.value).onSuccess {
                    _joinState.value = false
                }
            }
        }
    }

}