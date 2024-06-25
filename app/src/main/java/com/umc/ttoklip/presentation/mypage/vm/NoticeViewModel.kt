package com.umc.ttoklip.presentation.mypage.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.model.mypage.NoticeDetail
import com.umc.ttoklip.data.repository.mypage.MyPageRepository3Impl
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel @Inject constructor(
    private val repository:MyPageRepository3Impl
): ViewModel() {

    private val _noticeList=MutableStateFlow<List<NoticeDetail>>(listOf())
    val noticeList=_noticeList.asStateFlow()

    fun getNotice(){
        viewModelScope.launch {
            repository.getNotice()
                .onSuccess {
                    _noticeList.emit(it.notices)
                }
        }
    }

}