package com.umc.ttoklip.presentation.news

import kotlinx.coroutines.flow.StateFlow

interface NewsViewModel {
    val isExpanded : StateFlow<Boolean>


    fun expandedAppBar()
    fun collapsedAppBar()

    fun getMainNews()

    enum class NewsFilter{
        HOUSE_WORK,
        RECIPE,
        SAFE_LIFE,
        WELFARE
    }
}