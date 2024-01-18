package com.umc.ttoklip.presentation.home

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface HomeViewModel {
    val haveWork : StateFlow<Boolean>
    val doneWork : StateFlow<Boolean>
    val activityBus : SharedFlow<ActivityEventBus>

    fun clickDelayWork()
    fun clickDoneWork()
    fun clickMoreNews()
    fun clickMoreTip()
    fun clickAlarm()
    fun clickSearch()

    enum class ActivityEventBus{
        SEARCH,
        NEWS_DETAIL,
        TIP_DETAIL,
        ALARM
    }
}