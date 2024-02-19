package com.umc.ttoklip.presentation.hometown

import com.umc.ttoklip.data.model.town.Togethers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface TogetherViewModel {
    val filterRequiredAmount: StateFlow<Long>
    val filterMaxMember: StateFlow<Long>
    val showDialog: SharedFlow<Boolean>
    val page: StateFlow<Long>
    val togethers: StateFlow<List<Togethers>>

    fun onFilterClick()

    fun getFilters(requiredAmount: Long, maxMember: Long)
}