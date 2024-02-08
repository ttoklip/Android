package com.umc.ttoklip.presentation.news

import com.umc.ttoklip.data.model.news.News
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface NewsViewModel {
    val isExpanded : StateFlow<Boolean>
    val houseWorkList : StateFlow<List<News>>
    val recipeList : StateFlow<List<News>>
    val safeLivingList : StateFlow<List<News>>
    val welfarePolicyList : StateFlow<List<News>>

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