package com.umc.ttoklip.presentation.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.repository.news.NewsRepository
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModelImpl @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel(), NewsViewModel {

    private val _isExpanded = MutableStateFlow<Boolean>(false)
    override val isExpanded: StateFlow<Boolean>
        get() = _isExpanded

    override fun expandedAppBar() {
        viewModelScope.launch {
            _isExpanded.emit(true)
        }
    }

    override fun collapsedAppBar() {
        viewModelScope.launch {
            _isExpanded.emit(false)
        }
    }

    override fun getMainNews() {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.getNewsMain()
                .onSuccess {

                }.onFail {

            }
        }
    }


}