package com.umc.ttoklip.presentation.mypage.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.model.mypage.ParticipatedDeal
import com.umc.ttoklip.data.model.mypage.ScrapResponse
import com.umc.ttoklip.data.model.town.Togethers
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
class HistoryViewModel @Inject constructor(
    private val myPostRepository: MyPostRepository
) : ViewModel() {
    private val isEnd = MutableStateFlow(false)
    private val page = MutableStateFlow(0)

    private val _histories = MutableStateFlow(listOf<Togethers>())
    val histories: StateFlow<List<Togethers>>
        get() = _histories

    fun getMyHistories() {
        if (!isEnd.value) {
            viewModelScope.launch {
                try {
                    myPostRepository.getMyTogethers(
                        page = page.value
                    ).onSuccess {
                        _histories.emit(histories.value + it.carts)
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

}