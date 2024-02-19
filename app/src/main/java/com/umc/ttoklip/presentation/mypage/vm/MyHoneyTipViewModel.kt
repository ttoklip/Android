package com.umc.ttoklip.presentation.mypage.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.model.mypage.Community
import com.umc.ttoklip.data.model.mypage.CommunityX
import com.umc.ttoklip.data.model.mypage.HoneyTip
import com.umc.ttoklip.data.repository.mypage.MyPostRepository
import com.umc.ttoklip.module.onException
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyHoneyTipViewModel @Inject constructor(private val repository: MyPostRepository) :
    ViewModel() {
    private val isEnd = MutableStateFlow(false)
    private val page = MutableStateFlow(0)

    private val _questionList = MutableStateFlow(listOf<Community>())
    val questionList: StateFlow<List<Community>>
        get() = _questionList

    private val _honeyTipList = MutableStateFlow(listOf<HoneyTip>())
    val honeyTipList: StateFlow<List<HoneyTip>>
        get() = _honeyTipList

    private val _commsList = MutableStateFlow(listOf<CommunityX>())
    val commsList: StateFlow<List<CommunityX>>
        get() = _commsList


    fun getMyComms() {
        if (!isEnd.value) {
            viewModelScope.launch {
                try {
                    repository.getMyCommunications(
                        page = page.value
                    ).onSuccess {
                        _commsList.emit(commsList.value + it.communities)
                        page.value = page.value + 1
                        isEnd.value = it.isLast
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

    fun getMyHoneyTips() {
        if (!isEnd.value) {
            viewModelScope.launch {
                try {
                    repository.getMyHoneyTips(
                        page = page.value
                    ).onSuccess {
                        _honeyTipList.emit(honeyTipList.value + it.honeyTips)
                        page.value = page.value + 1
                        isEnd.value = it.isLast
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

    fun getMyQuestions() {
        if (!isEnd.value) {
            viewModelScope.launch {
                try {
                    repository.getMyQuestions(
                        page = page.value
                    ).onSuccess {
                        _questionList.emit(questionList.value + it.questions)
                        page.value = page.value + 1
                        isEnd.value = it.isLast
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

    fun reset() {
        viewModelScope.launch {
            _honeyTipList.emit(listOf())
            _commsList.emit(listOf())
            _questionList.emit(listOf())
            isEnd.value = false
            page.value = 0
        }
    }
}