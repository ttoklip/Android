package com.umc.ttoklip.presentation.honeytip

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.umc.ttoklip.presentation.honeytip.adapter.HoneyTips

class HoneyTipViewModel: ViewModel() {
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
}