package com.umc.ttoklip.presentation.otheruser

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.model.news.ReportRequest
import com.umc.ttoklip.data.model.stranger.OtherUserInfoResponse
import com.umc.ttoklip.data.repository.stranger.StrangerRepository
import com.umc.ttoklip.module.onException
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtherUserViewModel @Inject constructor(
    private val strangerRepository: StrangerRepository
) : ViewModel() {

    private val _strangerInfo = MutableStateFlow<OtherUserInfoResponse>(OtherUserInfoResponse())
    val strangerInfo: StateFlow<OtherUserInfoResponse>
        get() = _strangerInfo

     val event : SharedFlow<OtherEvent>
         get() = _event.asSharedFlow()

    private val _event = MutableSharedFlow<OtherEvent>()

    sealed class OtherEvent {
        data class ToastMessage(val message: String) : OtherEvent()
    }

    fun getStranger(nick: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                strangerRepository.getStranger(nick)
                    .onSuccess {
                        _strangerInfo.emit(it)
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

    fun postReportUser(request: ReportRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                strangerRepository.reportUser(strangerInfo.value.nickname, request).onSuccess {
                    _event.emit(OtherEvent.ToastMessage(it.message))
                }.onFail {
                    _event.emit((OtherEvent.ToastMessage("신고 사유를 알려주세요")))
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