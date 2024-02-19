package com.umc.ttoklip.presentation.hometown

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface TogetherViewModel {
    val filterRequiredAmount: StateFlow<Long>
    val filterMaxMember: StateFlow<Long>
    val showDialog: SharedFlow<Boolean>

    fun onFilterClick()

    fun getFilters(requiredAmount: Long, maxMember: Long)
}