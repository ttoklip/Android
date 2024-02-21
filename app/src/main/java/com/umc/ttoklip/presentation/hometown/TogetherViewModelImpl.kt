package com.umc.ttoklip.presentation.hometown

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.model.town.Togethers
import com.umc.ttoklip.data.model.town.TogethersResponse
import com.umc.ttoklip.data.repository.town.MainTogethersRepository
import com.umc.ttoklip.module.onError
import com.umc.ttoklip.module.onException
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
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

    private val _page: MutableStateFlow<Long> = MutableStateFlow(0L)
    override val page: StateFlow<Long>
        get() = _page

    private val _togethers: MutableStateFlow<List<Togethers>> = MutableStateFlow(emptyList())
    override val togethers: StateFlow<List<Togethers>>
        get() = _togethers

    private val _mainData = MutableStateFlow(TogethersResponse())
    override val mainData: StateFlow<TogethersResponse>
        get() = _mainData

    override fun onFilterClick() {
        viewModelScope.launch {
            _showDialog.emit(true)
        }
    }

    override fun get() {
        viewModelScope.launch {
            try {
                repository.getTogethers(0,1,10000000000,1,20)
                    .onSuccess {
                        _mainData.emit(it)
                        Log.d("emit", "emit")
                    }.onFail {

                    }.onException {
                        throw it
                    }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("예외", "$e")
            }
        }
    }

    override fun getFilters(requiredAmount: Long, maxMember: Long) {
        viewModelScope.launch {
            _filterRequiredAmount.value = requiredAmount
            _filterMaxMember.value = maxMember
            var require = when (requiredAmount) {
                1L -> {
                    10000L
                }

                2L -> {
                    20000L
                }

                3L -> {
                    30000L
                }

                4L -> {
                    40000L
                }

                5L -> {
                    50000L
                }

                else -> {
                    0L
                }
            }
            var min = 1L
            var max = 20L
            when (maxMember) {
                1L -> {
                    min = 2L
                    max = 4L
                }

                2L -> {
                    min = 5L
                    max = 7L
                }

                3L -> {
                    min = 8L
                    max = 10L
                }

                4L -> {
                    min = 11L
                    max = 13L
                }

                5L -> {
                    min = 14L
                    max = 17L
                }

                6L -> {
                    min = 18L
                    max = 20L
                }

                else -> {

                }
            }
            repository.getTogethers(page.value, require, 10000000000, min, max).onSuccess {
                _togethers.value = it.carts
            }.onError {
                Log.d("error", it.printStackTrace().toString())
            }
        }
    }
}