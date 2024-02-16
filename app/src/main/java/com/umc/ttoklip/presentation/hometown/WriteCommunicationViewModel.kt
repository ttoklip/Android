package com.umc.ttoklip.presentation.hometown

import com.umc.ttoklip.presentation.honeytip.adapter.Image
import kotlinx.coroutines.flow.StateFlow

interface WriteCommunicationViewModel {
    val title: StateFlow<String>
    val content: StateFlow<String>
    val closePage: StateFlow<Boolean>
    val doneButtonActivated: StateFlow<Boolean>
    val images: StateFlow<List<Image>>

    fun addImages(images: List<Image>)
    fun checkDone()
    fun doneButtonClick()
}