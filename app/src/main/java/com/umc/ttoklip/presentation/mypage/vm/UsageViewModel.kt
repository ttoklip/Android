package com.umc.ttoklip.presentation.mypage.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.model.mypage.MyBlockUserResponse
import com.umc.ttoklip.data.model.mypage.RestrictedResponse
import com.umc.ttoklip.data.repository.mypage.MyAccountManageUsageRepositoryImpl
import com.umc.ttoklip.module.onError
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsageViewModel @Inject constructor(
    private val repository: MyAccountManageUsageRepositoryImpl
):ViewModel(){

    private val _restrictedList = MutableStateFlow<Array<RestrictedResponse>>(arrayOf())
    val restrictList = _restrictedList.asStateFlow()

    private val _blockList = MutableStateFlow<MyBlockUserResponse>(MyBlockUserResponse())
    val blockList = _restrictedList.asStateFlow()

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
                    _blockList.emit(it)
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