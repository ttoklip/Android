package com.umc.ttoklip.presentation.otheruser

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.model.stranger.OtherUserInfoResponse
import com.umc.ttoklip.data.repository.stranger.StrangerRepository
import com.umc.ttoklip.module.onException
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtherUserViewModel @Inject constructor(
    private val strangerRepository: StrangerRepository
) : ViewModel() {

    private val _strangerInfo = MutableStateFlow<OtherUserInfoResponse>(OtherUserInfoResponse())
    val strangerInfo: StateFlow<OtherUserInfoResponse>
        get() = _strangerInfo

    fun getStranger(nick : String){
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
}