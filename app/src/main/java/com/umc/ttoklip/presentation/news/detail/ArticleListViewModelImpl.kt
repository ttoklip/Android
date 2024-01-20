package com.umc.ttoklip.presentation.news.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModelImpl @Inject constructor(

): ViewModel(), ArticleListViewModel {

    private val _filter = MutableStateFlow<ArticleListViewModel.NewsFilter>(ArticleListViewModel.NewsFilter.HOUSE_WORK)
    override val filter: StateFlow<ArticleListViewModel.NewsFilter>
        get() = _filter


    override fun selectHouseWork() {
        viewModelScope.launch {
            _filter.emit(ArticleListViewModel.NewsFilter.HOUSE_WORK)
        }
    }

    override fun selectRecipe() {
        viewModelScope.launch {
            _filter.emit(ArticleListViewModel.NewsFilter.RECIPE)
        }
    }

    override fun selectSafeLife() {
        viewModelScope.launch {
            _filter.emit(ArticleListViewModel.NewsFilter.SAFE_LIFE)
        }
    }

    override fun selectWelfare() {
        viewModelScope.launch {
            _filter.emit(ArticleListViewModel.NewsFilter.WELFARE)
        }
    }


}