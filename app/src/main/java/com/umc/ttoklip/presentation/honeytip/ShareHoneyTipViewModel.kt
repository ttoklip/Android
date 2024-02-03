package com.umc.ttoklip.presentation.honeytip

import kotlinx.coroutines.flow.StateFlow

interface ShareHoneyTipViewModel {
    val isExpanded : StateFlow<Boolean>


    fun expandedAppBar()
    fun collapsedAppBar()
}