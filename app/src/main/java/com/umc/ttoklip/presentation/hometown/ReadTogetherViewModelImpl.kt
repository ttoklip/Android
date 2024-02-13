package com.umc.ttoklip.presentation.hometown

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReadTogetherViewModelImpl @Inject constructor() : ViewModel(), ReadTogetherViewModel {
    private val _joinState = MutableStateFlow<Boolean>(true)
    override val joinState: StateFlow<Boolean>
        get() = _joinState

    private val _deadlineState = MutableStateFlow<Boolean>(true)
    override val deadlineState: StateFlow<Boolean>
        get() = _deadlineState

    private val _isOwner = MutableStateFlow<Boolean>(false)
    override val isOwner: StateFlow<Boolean>
        get() = _isOwner

    override fun joinBtnClick() {
        viewModelScope.launch {
            _joinState.emit(_joinState.value.not())
        }
    }

}