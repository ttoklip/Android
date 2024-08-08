package com.umc.ttoklip.presentation.otheruser

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.honeytip.HoneyTipMain
import com.umc.ttoklip.data.model.mypage.HoneyTip
import com.umc.ttoklip.data.repository.stranger.StrangerRepository
import com.umc.ttoklip.module.onException
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtherTipViewModel @Inject constructor(
    private val strangerRepository: StrangerRepository
) : ViewModel() {

    private val _tips = MutableStateFlow<List<HoneyTipMain>>(listOf())
    val tips: StateFlow<List<HoneyTipMain>>
        get() = _tips

    private val isEnd = MutableStateFlow(false)
    private val page = MutableStateFlow(0)

    fun getTips(id: Int) {
        if (isEnd.value.not()) {
            viewModelScope.launch {
                try {
                    strangerRepository.getStrangerTip(
                        page = page.value,
                        id = id
                    ).onSuccess {
                        _tips.emit(tips.value + it.honeyTips.map {
                            HoneyTipMain(
                                it.id,
                                it.title,
                                it.content,
                                it.writer,
                                it.likeCount,
                                it.commentCount,
                                it.scrapCount,
                                "",
                                it.writerProfileImageUrl
                            )
                        })
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