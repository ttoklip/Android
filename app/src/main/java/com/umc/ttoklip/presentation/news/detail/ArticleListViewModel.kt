package com.umc.ttoklip.presentation.news.detail

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface ArticleListViewModel {
    val filter : StateFlow<NewsFilter>

    fun selectHouseWork()
    fun selectRecipe()
    fun selectSafeLife()
    fun selectWelfare()


    enum class NewsFilter{
        HOUSE_WORK,
        RECIPE,
        SAFE_LIFE,
        WELFARE
    }

}