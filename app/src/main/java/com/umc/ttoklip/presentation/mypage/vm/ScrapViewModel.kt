package com.umc.ttoklip.presentation.mypage.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.model.mypage.ScrapResponse
import com.umc.ttoklip.data.model.search.SearchModel
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
    private val isTourEnd = MutableStateFlow(false)
    private val tourPage = MutableStateFlow(0)

    private val isTipEnd = MutableStateFlow(false)
    private val tipPage = MutableStateFlow(0)

    private val isNewsEnd = MutableStateFlow(false)
    private val newsPage = MutableStateFlow(0)

    private val _searchTipList = MutableStateFlow(listOf<ScrapResponse>())
    val searchTipList: StateFlow<List<ScrapResponse>>
        get() = _searchTipList

    private val _searchNewsList = MutableStateFlow(listOf<ScrapResponse>())

    val searchNewsList: StateFlow<List<ScrapResponse>>
        get() = _searchNewsList
    private val _searchTourList = MutableStateFlow(listOf<ScrapResponse>())

    val searchTourList: StateFlow<List<ScrapResponse>>
        get() = _searchTourList

    fun getNewsTown() {
        viewModelScope.launch {
            if (!isTourEnd.value) {
                viewModelScope.launch {
                    try {
                        scrapRepository.getTownScrap(
                            page = tourPage.value
                        ).onSuccess {
                            _searchTourList.emit(searchTourList.value + it.communities)
                            tourPage.value = tourPage.value + 1
                            isTourEnd.value = it.isLast
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
}