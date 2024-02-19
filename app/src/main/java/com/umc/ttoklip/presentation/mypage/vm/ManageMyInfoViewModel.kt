package com.umc.ttoklip.presentation.mypage.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.model.mypage.MyPageInfoResponse
import com.umc.ttoklip.data.repository.mypage.MyPageRepository2
import com.umc.ttoklip.data.repository.mypage.MyPageRepository2Impl
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageMyInfoViewModel @Inject constructor(
    private val repository: MyPageRepository2Impl
): ViewModel() {
    private val _myPageInfo = MutableStateFlow<MyPageInfoResponse>(MyPageInfoResponse())
    val myPageInfo = _myPageInfo.asStateFlow()

    val independentDuration = MutableStateFlow("")

    fun getMyPageInfo(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMyPageInfo().onSuccess {
                _myPageInfo.emit(it)
                independentDuration.emit("${it.independentYear}년 ${it.independentMonth}개월")
            }
        }
    }
}