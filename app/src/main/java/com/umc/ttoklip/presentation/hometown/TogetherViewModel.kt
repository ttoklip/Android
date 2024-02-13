package com.umc.ttoklip.presentation.hometown

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface TogetherViewModel {
    val filterSort: StateFlow<Int>
    val filterDuration: StateFlow<Int>
    val filterRequiredAmount: StateFlow<Int>
    val filterMaxMember: StateFlow<Int>
    val showDialog: SharedFlow<Boolean>

    fun onFilterClick()

    fun getFilters(sort: Int, duration: Int, requiredAmount: Int, maxMember: Int)
}