package com.umc.ttoklip.presentation.hometown

import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.town.townMainResponse
import kotlinx.coroutines.flow.StateFlow

interface MyHometownViewModel {

    val mainData : StateFlow<townMainResponse>

    fun getM()
}