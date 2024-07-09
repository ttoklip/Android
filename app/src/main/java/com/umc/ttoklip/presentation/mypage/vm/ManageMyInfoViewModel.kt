package com.umc.ttoklip.presentation.mypage.vm

import android.net.Uri
import android.security.identity.AccessControlProfile
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.model.mypage.MyPageInfoResponse
import com.umc.ttoklip.data.repository.mypage.MyPageRepository2
import com.umc.ttoklip.data.repository.mypage.MyPageRepository2Impl
import com.umc.ttoklip.module.onError
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import com.umc.ttoklip.presentation.honeytip.read.ReadHoneyTipViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class ManageMyInfoViewModel @Inject constructor(
    private val repository: MyPageRepository2Impl
) : ViewModel() {
    private val _myPageInfo = MutableStateFlow<MyPageInfoResponse>(MyPageInfoResponse())
    val myPageInfo = _myPageInfo.asStateFlow()

    private val _editMyPageInfo = MutableStateFlow<MyPageInfoResponse>(MyPageInfoResponse())
    val editMyPageInfo = _myPageInfo.asStateFlow()

    private val _myPageEvent = MutableSharedFlow<Event>()
    val myPageEvent = _myPageEvent.asSharedFlow()

    val independentDuration = MutableStateFlow("")

    private val _nickok = MutableSharedFlow<Boolean>()
    val nickok = _nickok.asSharedFlow()

    sealed class Event {
        object EditMyPageInfo : Event()
    }

    private fun convertStringToTextPlain(string: String): RequestBody {
        return string.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    private fun createRequestBodyFromList(list: List<String>): RequestBody {
        val listString = list.joinToString(",") // List를 쉼표로 구분된 문자열로 변환
        return RequestBody.create("text/plain".toMediaTypeOrNull(), listString)
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _myPageEvent.emit(event)
        }
    }

    fun getMyPageInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMyPageInfo().onSuccess {
                _myPageInfo.emit(it)
                independentDuration.emit("${it.independentYear}년 ${it.independentMonth}개월")
            }
        }
    }

    fun editMyPageInfo(
        street: String,
        nickname: String,
        categories: List<String>,
        profileImage: MultipartBody.Part?,
        independentYear: Int,
        independentMonth: Int
    ) {
        createRequestBodyFromList(categories)
        viewModelScope.launch(Dispatchers.IO) {
            repository.editMyPageInfo(
                convertStringToTextPlain(street),
                convertStringToTextPlain(nickname),
                createRequestBodyFromList(categories),
                profileImage,
                convertStringToTextPlain(independentYear.toString()),
                convertStringToTextPlain(independentMonth.toString())
            ).onSuccess {
                Log.d("edit info", it.toString())
                event(Event.EditMyPageInfo)
            }.onError {
                Log.d("error", it.toString())
            }
        }
    }

    fun nickCheck(nick: String) {
        viewModelScope.launch {
            repository.checkNickname(nick)
                .onSuccess {
                    Log.i("nick check", "성공")
                    _nickok.emit(true)
                }.onFail {
                    Log.d("nick check", "실패")
                    _nickok.emit(false)
                }
        }
    }
}