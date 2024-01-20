package com.umc.ttoklip.presentation.news

import kotlinx.coroutines.flow.StateFlow

interface NewsViewModel {


    enum class NewsFilter{
        HOUSE_WORK,
        RECIPE,
        SAFE_LIFE,
        WELFARE
    }
}