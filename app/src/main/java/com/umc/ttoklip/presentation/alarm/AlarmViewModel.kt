package com.umc.ttoklip.presentation.alarm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.data.model.home.NotificationItem
import com.umc.ttoklip.data.model.home.NotificationResponse
import com.umc.ttoklip.data.model.news.News
import com.umc.ttoklip.data.repository.home.HomeRepository
import com.umc.ttoklip.module.onException
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    private val _notiList = MutableStateFlow<List<NotificationItem>>(listOf())
    val notiList: StateFlow<List<NotificationItem>>
        get() = _notiList

    init {
        viewModelScope.launch {
            getNotificationsHoney()
            getNotificationsQuestion()
            getNotificationsTown()
            notiList.value.sortedBy { it.notificationId }
        }
    }

    private fun getNotificationsHoney() {
        viewModelScope.launch {
            try {
                repository.getNotifications("HONEY_TIP_COMMENT")
                    .onSuccess {
                        _notiList.emit(notiList.value + it.responses)
                        repository.getNotifications("HONEY_TIP_CHILD_COMMENT")
                            .onSuccess {
                                _notiList.emit(notiList.value + it.responses)
                            }.onFail {

                            }.onException {
                                throw it
                            }
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

    private fun getNotificationsQuestion() {
        viewModelScope.launch {
            try {
                repository.getNotifications("QUESTION_COMMENT")
                    .onSuccess {
                        _notiList.emit(notiList.value + it.responses)
                        repository.getNotifications("QUESTION_CHILD_COMMENT")
                            .onSuccess {
                                _notiList.emit(notiList.value + it.responses)
                            }.onFail {

                            }.onException {
                                throw it
                            }
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

    private fun getNotificationsTown() {
        viewModelScope.launch {
            try {
                repository.getNotifications("OUR_TOWN_COMMENT")
                    .onSuccess {
                        _notiList.emit(notiList.value + it.responses)
                        repository.getNotifications("OUR_TOWN_CHILD_COMMENT")
                            .onSuccess {
                                _notiList.emit(notiList.value + it.responses)
                            }.onFail {

                            }.onException {
                                throw it
                            }
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