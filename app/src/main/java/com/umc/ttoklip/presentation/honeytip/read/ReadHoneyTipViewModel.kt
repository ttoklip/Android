package com.umc.ttoklip.presentation.honeytip.read

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.model.honeytip.InquireHoneyTipResponse
import com.umc.ttoklip.data.model.honeytip.InquireQuestionResponse
import com.umc.ttoklip.data.model.honeytip.request.ReportRequest
import com.umc.ttoklip.data.repository.honeytip.HoneyTipRepositoryImpl
import com.umc.ttoklip.module.onError
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadHoneyTipViewModel @Inject constructor(
    private val repository: HoneyTipRepositoryImpl
): ViewModel(){
    private val _honeyTip = MutableStateFlow<InquireHoneyTipResponse>(
        InquireHoneyTipResponse(0, "", "", "", "", "", 0, 0, 0, emptyList(), emptyList(), false, false, emptyList())
    )
    val honeyTip = _honeyTip.asStateFlow()

    private val _readEvent = MutableSharedFlow<ReadEvent>()
    val readEvent = _readEvent.asSharedFlow()

    private val _menuEvent = MutableSharedFlow<MenuEvent>()
    val menuEvent = _menuEvent.asSharedFlow()

    val replyCommentParentId = MutableStateFlow(0)
    val commentContent = MutableStateFlow("")

    private val _isLike = MutableStateFlow(false)
    val isLike = _isLike.asStateFlow()

    private val _isScrap = MutableStateFlow(false)
    val isScrap = _isScrap.asStateFlow()


    sealed class ReadEvent{
        data class ReadHoneyTipEvent(val inquireHoneyTipResponse: InquireHoneyTipResponse): ReadEvent()
        data class ReadQuestionEvent(val inquireQuestionResponse: InquireQuestionResponse): ReadEvent()
    }

    sealed class MenuEvent{
        object DeleteHoneyTip: MenuEvent()
        object ReportHoneyTip: MenuEvent()
        object ReportQuestion: MenuEvent()

        object PostScrap: MenuEvent()
        object DeleteScrap: MenuEvent()

        object PostLike: MenuEvent()
        object DeleteLike: MenuEvent()
    }

    private fun eventRead(event: ReadEvent) {
        viewModelScope.launch {
            _readEvent.emit(event)
        }
    }

    private fun eventMenu(event: MenuEvent) {
        viewModelScope.launch {
            _menuEvent.emit(event)
        }
    }

    fun inquireHoneyTip(honeyTipId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.inquireHoneyTip(honeyTipId).onSuccess {
                Log.d("emit success", "success")
                _honeyTip.emit(it)
                eventRead(ReadEvent.ReadHoneyTipEvent(_honeyTip.value))
                _isScrap.emit(it.scrapedByCurrentUser)
                _isLike.emit(it.likedByCurrentUser)
                Log.d("inquirehoneytip", it.toString())
            }.onError { Log.d("error", it.stackTraceToString()) }
        }
    }

    fun reportHoneyTip(honeyTipId: Int, request: ReportRequest){
        viewModelScope.launch(Dispatchers.IO) {
            repository.reportHoneyTip(honeyTipId, request).onSuccess {
                Log.d("report HoneyTip", it.toString())
                eventMenu(MenuEvent.ReportHoneyTip)
            }
        }
    }

    fun deleteHoneyTip(honeyTipId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteHoneyTip(honeyTipId).onSuccess {
                Log.d("delete honeyTip", it.toString())
                eventMenu(MenuEvent.DeleteHoneyTip)
            }
        }
    }

    fun postScrap(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.scrapHoneyTip(_honeyTip.value.honeyTipId).onSuccess {
                _isScrap.emit(true)
                _honeyTip.emit(honeyTip.value.copy().also {
                    it.scrapCount += 1
                })
                eventMenu(MenuEvent.PostScrap)
            }
        }
    }

    fun deleteScrap(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteScrapHoneyTip(honeyTip.value.honeyTipId).onSuccess {
                _isScrap.emit(false)
                _honeyTip.emit(honeyTip.value.copy().also {
                    it.scrapCount -= 1
                })
                eventMenu(MenuEvent.DeleteScrap)
            }
        }
    }

    fun postLike(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.likeHoneyTip(honeyTip.value.honeyTipId).onSuccess {
                _isLike.emit(true)
                _honeyTip.emit(honeyTip.value.copy().also {
                    it.likeCount += 1
                })
                eventMenu(MenuEvent.PostLike)
            }
        }
    }

    fun deleteLike(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.likeHoneyTip(honeyTip.value.honeyTipId).onSuccess {
                _isLike.emit(false)
                _honeyTip.emit(honeyTip.value.copy().also {
                    it.likeCount -= 1
                })
                eventMenu(MenuEvent.DeleteLike)
            }
        }
    }

    fun inquireQuestion(questionId: Int){
        viewModelScope.launch(Dispatchers.IO){
            repository.inquireQuestion(questionId).onSuccess {
                eventRead(ReadEvent.ReadQuestionEvent(it))
            }
        }
    }

    fun reportQuestion(questionId: Int, request: ReportRequest){
        viewModelScope.launch(Dispatchers.IO) {
            repository.reportQuestion(questionId, request).onSuccess {
                Log.d("report Question", it.toString())
                eventMenu(MenuEvent.ReportQuestion)
            }
        }
    }
}