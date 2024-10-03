package com.umc.ttoklip.presentation.hometown.together

import com.umc.ttoklip.data.model.town.Togethers
import com.umc.ttoklip.data.model.town.TogethersResponse
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface TogetherViewModel {
    val filterRequiredAmount: StateFlow<Long>
    val filterMaxMember: StateFlow<Long>
    val showDialog: SharedFlow<Boolean>
    val togethers: StateFlow<List<Togethers>>
    val mainData : SharedFlow<TogethersResponse>
    val criteria: StateFlow<String>
    val streetInfo: StateFlow<String>

    fun setCriteria(position: Int)

    fun onFilterClick()

    fun getTogether()


    fun getFilters(requiredAmount: Long, maxMember: Long)

    fun getMemberStreetInfo()
}