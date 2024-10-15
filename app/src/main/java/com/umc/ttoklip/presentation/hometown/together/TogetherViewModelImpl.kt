package com.umc.ttoklip.presentation.hometown.together

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class TogetherViewModelImpl @Inject constructor(
    private val repository: MainTogethersRepository,
    private val mainTogethersRepository: MainTogethersRepository
) :
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


    private val _togethers: MutableStateFlow<List<Togethers>> = MutableStateFlow(emptyList())
    override val togethers: StateFlow<List<Togethers>>
        get() = _togethers

    private val _mainData = MutableSharedFlow<TogethersResponse>()
    override val mainData: SharedFlow<TogethersResponse>
        get() = _mainData

    private val _criteria = MutableStateFlow("CITY")
    override val criteria: StateFlow<String>
        get() = _criteria

    private val _streetInfo = MutableStateFlow<List<String>>(emptyList())
    override val streetInfo: StateFlow<List<String>>
        get() = _streetInfo

    override fun setCriteria(position: Int) {
        _criteria.value = when (position) {
            0 -> "CITY"
            1 -> "DISTRICT"
            2 -> "TOWN"
            else -> throw IllegalArgumentException()
        }

        if (_criteria.value.isNotEmpty()) {
            _togethers.value = listOf()
            page.value = 0
            isEnd.value = false
            getTogether()
        }
    }

    private val isEnd = MutableStateFlow<Boolean>(false)

    private val page = MutableStateFlow<Int>(0)


    override fun onFilterClick() {
        viewModelScope.launch {
            _showDialog.emit(true)
        }
    }

    override fun getFilters(requiredAmount: Long, maxMember: Long) {
        viewModelScope.launch {
            _filterRequiredAmount.value = requiredAmount
            _filterMaxMember.value = maxMember
            _togethers.value = listOf()
            page.value = 0
            isEnd.value = false
            getTogether()
        }
    }

    override fun getMemberStreetInfo() {
        viewModelScope.launch {
            mainTogethersRepository.getMemberStreetInfo().onSuccess {
                _streetInfo.value = it.street.split(" ")
                Log.d("street INfo", it.street)
            }
        }
    }

    override fun getTogether() {
        viewModelScope.launch {
            var minAmount = 0L
            var maxAmount = 100000000L
            when (filterRequiredAmount.value) {
                1L -> {
                    minAmount = 0L
                    maxAmount = 10000L
                }

                2L -> {
                    minAmount = 0L
                    maxAmount = 20000L
                }

                3L -> {
                    minAmount = 0L
                    maxAmount = 30000L
                }

                4L -> {
                    minAmount = 0L
                    maxAmount = 40000L
                }

                5L -> {
                    minAmount = 50000L
                    maxAmount = 100000000L

                }

                else -> {
                    minAmount = 0L
                    maxAmount = 100000000L
                }
            }
            var min = 1L
            var max = 20L
            when (filterMaxMember.value) {
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
                    min = 1L
                    max = 20L
                }
            }


            if (!isEnd.value) {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        repository.getTogethers(
                            page.value,
                            minAmount,
                            maxAmount,
                            min,
                            max,
                            criteria.value
                        )
                            .onSuccess {
                                _togethers.value = togethers.value + it.carts
                                page.value += 1
                                isEnd.value = it.isLast
                            }.onError {
                                Log.d("error", it.printStackTrace().toString())
                            }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.d("예외", "$e")
                    }
                }
            }
        }
    }
}