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
import com.umc.ttoklip.presentation.honeytip.write.WriteHoneyTipViewModel
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
        InquireHoneyTipResponse(0, "", "", "", "", "", emptyList(), emptyList(), false, false, emptyList())
    )
    val honeyTip = _honeyTip.asStateFlow()

    private val _readEvent = MutableSharedFlow<ReadEvent>()
    val readEvent = _readEvent.asSharedFlow()

    sealed class ReadEvent{
        data class ReadHoneyTipEvent(val inquireHoneyTipResponse: InquireHoneyTipResponse): ReadEvent()
        data class ReadQuestionEvent(val inquireQuestionResponse: InquireQuestionResponse): ReadEvent()

        object DeleteHoneyTip: ReadEvent()
        object ReportHoneyTip: ReadEvent()
        object ReportQuestion: ReadEvent()

    }

    private fun event(event: ReadEvent) {
        viewModelScope.launch {
            _readEvent.emit(event)
        }
    }

    fun inquireHoneyTip(honeyTipId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.inquireHoneyTip(honeyTipId).onSuccess {
                Log.d("emit success", "success")
                event(ReadEvent.ReadHoneyTipEvent(it))
                Log.d("inquirehoneytip", it.toString())
            }.onError { Log.d("error", it.stackTraceToString()) }
        }
    }

    fun reportHoneyTip(honeyTipId: Int, request: ReportRequest){
        viewModelScope.launch(Dispatchers.IO) {
            repository.reportHoneyTip(honeyTipId, request).onSuccess {
                Log.d("report HoneyTip", it.toString())
                event(ReadEvent.ReportHoneyTip)
            }
        }
    }

    fun deleteHoneyTip(honeyTipId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteHoneyTip(honeyTipId).onSuccess {
                Log.d("delete honeyTip", it.toString())
                event(ReadEvent.DeleteHoneyTip)
            }
        }
    }

    fun scrapHoneyTip(honeyTipId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.scrapHoneyTip(honeyTipId).onSuccess {
                Log.d("scrap honeyTip", it.toString())
            }
        }
    }

    fun inquireQuestion(questionId: Int){
        viewModelScope.launch(Dispatchers.IO){
            repository.inquireQuestion(questionId).onSuccess {
                event(ReadEvent.ReadQuestionEvent(it))
            }
        }
    }

    fun reportQuestion(questionId: Int, request: ReportRequest){
        viewModelScope.launch(Dispatchers.IO) {
            repository.reportQuestion(questionId, request).onSuccess {
                Log.d("report Question", it.toString())
                event(ReadEvent.ReportQuestion)
            }
        }
    }
}