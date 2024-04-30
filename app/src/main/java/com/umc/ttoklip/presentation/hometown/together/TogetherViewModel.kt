package com.umc.ttoklip.presentation.hometown.together

import com.umc.ttoklip.data.model.town.Togethers
import com.umc.ttoklip.data.model.town.TogethersResponse
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface TogetherViewModel {
    val filterRequiredAmount: StateFlow<Long>
    val filterMaxMember: StateFlow<Long>
    val showDialog: SharedFlow<Boolean>
    val page: StateFlow<Long>
    val togethers: StateFlow<List<Togethers>>
    val mainData : StateFlow<TogethersResponse>

    fun onFilterClick()

    fun get()

    fun getFilters(requiredAmount: Long, maxMember: Long)
}