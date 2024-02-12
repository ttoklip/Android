package com.umc.ttoklip.presentation.honeytip

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.repository.honeytip.HoneyTipRepositoryImpl
import com.umc.ttoklip.module.onSuccess
import com.umc.ttoklip.presentation.honeytip.adapter.HoneyTips
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class HoneyTipViewModel @Inject constructor(
    private val repository: HoneyTipRepositoryImpl
): ViewModel() {
    val boardLiveData: LiveData<String> by lazy { _boardLiveData }
    private val _boardLiveData by lazy { MutableLiveData<String>("꿀팁 공유") }

    fun setBoardLiveData(board: String){
        _boardLiveData.value = board
        Log.d("viewModel", board)
    }

    val isTitleNull: LiveData<Boolean> by lazy { _isTitleNull }
    private val _isTitleNull by lazy { MutableLiveData<Boolean>(true) }

    val isBodyNull: LiveData<Boolean> by lazy { _isBodyNull }
    private val _isBodyNull by lazy { MutableLiveData<Boolean>(true) }

    val honeyTipLiveData: LiveData<HoneyTips> by lazy { _honeyTipLiveData }
    private val _honeyTipLiveData by lazy { MutableLiveData<HoneyTips>() }

    val createHoneyTipMessage: LiveData<String> by lazy { _createHoneyTipMessage }
    private val _createHoneyTipMessage by lazy { MutableLiveData<String>() }

    private val _writeDoneEvent = MutableSharedFlow<String>()
    val writeDoneEvent = _writeDoneEvent.asSharedFlow()

    private val _changeBoardEvent = MutableStateFlow<String>("꿀팁 공유")
    val changeBoardEvent = _changeBoardEvent.asStateFlow()
    fun setHoneyTip(honeyTip: HoneyTips){
        _honeyTipLiveData.value?.title = honeyTip.title
        _honeyTipLiveData.value?.writer = "test"
        _honeyTipLiveData.value?.body = honeyTip.title
        _honeyTipLiveData.value?.date = "2일전"
        _honeyTipLiveData.value?.chatCnt = 2
    }
    fun setTitle(boolean: Boolean){
        _isTitleNull.value = boolean
    }

    fun setBody(boolean: Boolean){
        _isBodyNull.value = boolean
    }

    fun createHoneyTip(title: RequestBody, content: RequestBody, category: RequestBody, images: Array<MultipartBody.Part>, uri: RequestBody){
        viewModelScope.launch(Dispatchers.IO) {
            repository.createHoneyTip(title, content, category, images, uri).onSuccess {
                withContext(Dispatchers.Main) {
                    _writeDoneEvent.emit("true")
                    _createHoneyTipMessage.value = it.message
                    Log.d("honey tip api test", it.message)
                }
            }
        }
    }

    fun createQuestion(title: RequestBody, content: RequestBody, category: RequestBody, images: Array<MultipartBody.Part>){
        viewModelScope.launch(Dispatchers.IO){
            repository.createQuestion(title, content, category, images).onSuccess {
                withContext(Dispatchers.Main){
                    Log.d("question", it.message)
                }
            }
        }
    }
}