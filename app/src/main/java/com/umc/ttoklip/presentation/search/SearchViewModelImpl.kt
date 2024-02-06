package com.umc.ttoklip.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModelImpl @Inject constructor(): ViewModel(), SearchViewModel  {

    private val _showDialog = MutableSharedFlow<Boolean>()
    private val _filterSort = MutableStateFlow(1)
    override val filterSort: StateFlow<Int>
        get() = _filterSort
    private val _filterBoard = MutableStateFlow(0)
    override val filterBoard: StateFlow<Int>
        get() = _filterBoard

    private val _filterCategory  = MutableStateFlow(0)
    override val filterCategory: StateFlow<Int>
        get() = _filterCategory
    override val showDialog: SharedFlow<Boolean>
        get() = _showDialog

    override fun clickFilter() {
        viewModelScope.launch {
            _showDialog.emit(true)
        }
    }

    override fun filter(sort: Int, board: Int, category:Int) {
        viewModelScope.launch {
            _filterSort.value = sort
            _filterBoard.value =board
            _filterCategory.value =category
        }
    }
}