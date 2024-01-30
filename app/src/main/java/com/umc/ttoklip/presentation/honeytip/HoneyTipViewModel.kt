package com.umc.ttoklip.presentation.honeytip

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HoneyTipViewModel: ViewModel() {
    val boardLiveData: LiveData<String> by lazy { _boardLiveData }
    private val _boardLiveData by lazy { MutableLiveData<String>("꿀팁 공유") }

    fun setBoardLiveData(board: String){
        _boardLiveData.value = board
    }

    val titleLiveData: LiveData<String> by lazy { _titleLiveData }
    private val _titleLiveData by lazy { MutableLiveData<String>() }

    val bodyLiveData: LiveData<String> by lazy { _bodyLiveData }
    private val _bodyLiveData by lazy { MutableLiveData<String>() }

    fun setTitle(title: String){
        _titleLiveData.value = title
    }

    fun setBody(body: String){
        _bodyLiveData.value = body
    }
}