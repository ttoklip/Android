package com.umc.ttoklip.presentation.hometown

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.model.town.CommentResponse
import com.umc.ttoklip.data.model.town.CreateCommentRequest
import com.umc.ttoklip.data.model.town.ReportRequest
import com.umc.ttoklip.data.model.town.ViewCommunicationResponse
import com.umc.ttoklip.data.repository.town.ReadCommsRepository
import com.umc.ttoklip.module.onError
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadCommunicationViewModelImpl @Inject constructor(
    private val repository: ReadCommsRepository
) : ViewModel(),
    ReadCommunicationVIewModel {
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

    override fun savePostId(postId: Long) {
        _postId.value = postId
    }

    override fun readCommunication(postId: Long) {
        viewModelScope.launch {
            repository.viewComms(postId).onSuccess {
                _postContent.value = it
            }.onError {

            }
        }
    }

    override fun changeScrap() {
        _like.value = _like.value.not()
        viewModelScope.launch {
            if (_like.value) {
                repository.addCommsLike(postId.value)
            } else {
                repository.cancelCommsLike(postId.value)
            }
        }
    }

    override fun changeLike() {
        _scrap.value = _scrap.value.not()
        viewModelScope.launch {
            if (_scrap.value) {
                repository.addCommsScrap(postId.value)
            } else {
                repository.cancelCommsScrap(postId.value)
            }
        }
    }

    override fun reportPost(reportRequest: ReportRequest) {
        viewModelScope.launch {
            if (postId.value != 0L) {
                repository.reportComms(postId.value, reportRequest)
            }
        }
    }

    override fun reportComment(commentId: Long, reportRequest: ReportRequest) {
        viewModelScope.launch {
            repository.reportCommsComment(commentId, reportRequest)
        }
    }

    override fun deleteComment(commentId: Long) {
        viewModelScope.launch {
            repository.deleteCommsComment(commentId)
        }
    }

    override fun createComment(body: CreateCommentRequest) {
        viewModelScope.launch {
            if (postId.value != 0L) {
                repository.createCommsComment(postId.value, body)
            }
        }
    }

}