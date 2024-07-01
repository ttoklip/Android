package com.umc.ttoklip.presentation.mypage.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.model.mypage.BlockedUser
import com.umc.ttoklip.data.model.mypage.RestrictedResponse
import com.umc.ttoklip.data.repository.mypage.MyPageRepository3Impl
import com.umc.ttoklip.module.onError
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsageViewModel @Inject constructor(
    private val repository: MyPageRepository3Impl
):ViewModel(){

    private val _restrictedList = MutableStateFlow<List<RestrictedResponse>>(listOf())
    val restrictList = _restrictedList.asStateFlow()

    private val _blockList = MutableStateFlow<List<BlockedUser>>(listOf())
    val blockList = _blockList.asStateFlow()

    fun getRestrict(){
        viewModelScope.launch {
            repository.getRestrictedReason()
                .onSuccess {
//                    _restrictedList.emit()
                }
        }
    }

    fun getBlockUser(){
        viewModelScope.launch {
            repository.getBlockedUser()
                .onSuccess {
                    _blockList.emit(it.blockedUsers)
                }.onError {
                    Log.d("error", it.toString())
                }
        }
    }

    fun unBlockUser(userId:Long){
        viewModelScope.launch {
            repository.deleteBlockUser(userId)
                .onSuccess {
                    getBlockUser()
                }.onError {
                    Log.d("error",it.toString())
                }
        }
    }

}