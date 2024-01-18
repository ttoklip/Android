package com.umc.ttoklip.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.presentation.search.SearchActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@HiltViewModel
class HomeViewModelImpl @Inject constructor(

): ViewModel(),HomeViewModel {

    private val _haveWork: MutableStateFlow<Boolean> = MutableStateFlow(true)
    override val haveWork: StateFlow<Boolean>
        get() = _haveWork

    private val _doneWork : MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val doneWork: StateFlow<Boolean>
        get() = _doneWork

    private val _activityBus = MutableSharedFlow<HomeViewModel.ActivityEventBus>()
    override val activityBus: SharedFlow<HomeViewModel.ActivityEventBus>
        get() = _activityBus.asSharedFlow()

    override fun clickDelayWork() {
        viewModelScope.launch {
            _haveWork.emit(haveWork.value.not())
        }
    }

    override fun clickDoneWork() {
        viewModelScope.launch {
            _doneWork.emit(doneWork.value.not())
        }
    }

    override fun clickMoreNews() {
        viewModelScope.launch {
            _activityBus.emit(HomeViewModel.ActivityEventBus.NEWS_DETAIL)
        }
    }

    override fun clickMoreTip() {
        viewModelScope.launch {
            _activityBus.emit(HomeViewModel.ActivityEventBus.TIP_DETAIL)
        }
    }

    override fun clickAlarm() {
        viewModelScope.launch {
            _activityBus.emit(HomeViewModel.ActivityEventBus.ALARM)
        }
    }

    override fun clickSearch() {
        viewModelScope.launch {
            _activityBus.emit(HomeViewModel.ActivityEventBus.SEARCH)
        }
    }

}