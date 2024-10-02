package com.umc.ttoklip.presentation.home

import com.umc.ttoklip.data.model.home.HomeResponse
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface HomeViewModel {
    val haveWork: StateFlow<Boolean>
    val doneWork: StateFlow<Boolean>
    val activityBus: SharedFlow<ActivityEventBus>
    val mainData: StateFlow<HomeResponse>

    fun clickDelayWork()
    fun clickDoneWork()
    fun clickMoreNews()
    fun clickMoreTip()
    fun clickMoreGroupBuy()
    fun clickAlarm()
    fun clickSearch()
    fun getMain(expirationToken: ()->Unit)
    fun patchFCM(token: String)



    enum class ActivityEventBus {
        SEARCH,
        NEWS_DETAIL,
        TIP_DETAIL,
        ALARM,
        GROUP_BUY_DETAIL
    }
}