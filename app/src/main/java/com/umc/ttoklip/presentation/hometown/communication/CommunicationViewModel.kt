package com.umc.ttoklip.presentation.hometown.communication

import com.umc.ttoklip.data.model.town.Communities
import kotlinx.coroutines.flow.StateFlow

interface CommunicationViewModel {
    val communities: StateFlow<List<Communities>>
    val criteria: StateFlow<String>

    fun setCriteria(criteria: String)
    fun getCommunities()
}