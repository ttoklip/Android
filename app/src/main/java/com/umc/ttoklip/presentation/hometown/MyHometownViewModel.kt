package com.umc.ttoklip.presentation.hometown

import com.umc.ttoklip.data.model.town.TownMainResponse
import com.umc.ttoklip.util.UiState
import kotlinx.coroutines.flow.StateFlow

interface MyHometownViewModel {

    val mainData : StateFlow<UiState<TownMainResponse>>
    val errorData: StateFlow<String>

    fun getM()
}