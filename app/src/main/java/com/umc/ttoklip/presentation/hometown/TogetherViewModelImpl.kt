package com.umc.ttoklip.presentation.hometown

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.repository.town.MainTogethersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TogetherViewModelImpl @Inject constructor(private val repository: MainTogethersRepository) :
    ViewModel(), TogetherViewModel {
    private val _filterRequiredAmount = MutableStateFlow(0L)
    override val filterRequiredAmount: StateFlow<Long>
        get() = _filterRequiredAmount

    private val _filterMaxMember = MutableStateFlow(0L)
    override val filterMaxMember: StateFlow<Long>
        get() = _filterMaxMember

    private val _showDialog = MutableSharedFlow<Boolean>()
    override val showDialog: SharedFlow<Boolean>
        get() = _showDialog

    override fun onFilterClick() {
        viewModelScope.launch {
            _showDialog.emit(true)
        }
    }

    override fun getFilters(requiredAmount: Long, maxMember: Long) {
        viewModelScope.launch {
            _filterRequiredAmount.value = requiredAmount
            _filterMaxMember.value = maxMember
        }
    }
}