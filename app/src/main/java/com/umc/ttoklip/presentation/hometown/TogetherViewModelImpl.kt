package com.umc.ttoklip.presentation.hometown

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
class TogetherViewModelImpl @Inject constructor() : ViewModel(), TogetherViewModel {
    private val _filterRequiredAmount = MutableStateFlow(0)
    override val filterRequiredAmount: StateFlow<Int>
        get() = _filterRequiredAmount

    private val _filterMaxMember = MutableStateFlow(0)
    override val filterMaxMember: StateFlow<Int>
        get() = _filterMaxMember

    private val _showDialog = MutableSharedFlow<Boolean>()
    override val showDialog: SharedFlow<Boolean>
        get() = _showDialog

    override fun onFilterClick() {
        viewModelScope.launch {
            _showDialog.emit(true)
        }
    }

    override fun getFilters(requiredAmount: Int, maxMember: Int) {
        viewModelScope.launch {
            _filterRequiredAmount.value = requiredAmount
            _filterMaxMember.value = maxMember
        }
    }
}