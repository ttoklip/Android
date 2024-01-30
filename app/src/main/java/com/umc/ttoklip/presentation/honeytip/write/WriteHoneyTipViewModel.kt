package com.umc.ttoklip.presentation.honeytip.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WriteHoneyTipViewModel: ViewModel() {
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