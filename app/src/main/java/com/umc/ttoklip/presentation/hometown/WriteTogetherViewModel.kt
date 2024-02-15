package com.umc.ttoklip.presentation.hometown

import kotlinx.coroutines.flow.StateFlow

interface WriteTogetherViewModel {
    val totalPrice: StateFlow<Long>
    val totalMember: StateFlow<Long>
    val title: StateFlow<String>
    val dealPlace: StateFlow<String>
    val openLink: StateFlow<String>
    val content: StateFlow<String>
    val extraUrl: StateFlow<String>
    val doneButtonActivated: StateFlow<Boolean>
    val doneWriteTogether: StateFlow<Boolean>

    fun setTotalPrice(totalPrice: Long)
    fun setTotalMember(totalMember: Long)
    fun checkDone()
    fun doneButtonClicked()
}