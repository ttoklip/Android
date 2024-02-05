package com.umc.ttoklip.presentation.hometown

import kotlinx.coroutines.flow.StateFlow

interface ReadTogetherViewModel {
    val joinState: StateFlow<Boolean>
    val deadlineState: StateFlow<Boolean>
    val isOwner: StateFlow<Boolean>

    fun joinBtnClick()
}