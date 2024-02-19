package com.umc.ttoklip.presentation.mypage.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.model.mypage.ScrapResponse
import com.umc.ttoklip.data.repository.scrap.ScrapRepository
import com.umc.ttoklip.module.onException
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScrapViewModel @Inject constructor(
    val scrapRepository: ScrapRepository
) : ViewModel() {
    private val isEnd = MutableStateFlow(false)
    private val page = MutableStateFlow(0)

    private val _scrapList = MutableStateFlow(listOf<ScrapResponse>())
    val scrapList: StateFlow<List<ScrapResponse>>
        get() = _scrapList

    fun getTownScrap() {
        if (!isEnd.value) {
            viewModelScope.launch {
                try {
                    scrapRepository.getTownScrap(
                        page = page.value
                    ).onSuccess {
                        _scrapList.emit(scrapList.value + it.communities)
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

    fun getNewsScrap() {
        if (!isEnd.value) {
            viewModelScope.launch {
                try {
                    scrapRepository.getNewsScrap(
                        page = page.value
                    ).onSuccess {
                        _scrapList.emit(scrapList.value + it.newsletters)
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

    fun getTipScrap() {
        if (!isEnd.value) {
            viewModelScope.launch {
                try {
                    scrapRepository.getTipScrap(
                        page = page.value
                    ).onSuccess {
                        _scrapList.emit(scrapList.value + it.honeyTips)
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
            _scrapList.emit(listOf())
            isEnd.value = false
            page.value = 0
        }
    }
}