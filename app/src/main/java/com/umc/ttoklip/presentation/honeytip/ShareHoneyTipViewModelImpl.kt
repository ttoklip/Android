package com.umc.ttoklip.presentation.honeytip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.presentation.news.NewsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShareHoneyTipViewModelImpl @Inject constructor(

) : ViewModel(), ShareHoneyTipViewModel {

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
}