package com.umc.ttoklip.presentation.hometown.together.write

import com.naver.maps.geometry.LatLng
import com.umc.ttoklip.presentation.honeytip.adapter.Image
import kotlinx.coroutines.flow.SharedFlow
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
    val closePage: StateFlow<Boolean>
    val images: StateFlow<List<Image>>
    val postId: StateFlow<Long>
    val address: StateFlow<String>
    val addressDetail: StateFlow<String>
    val isInputComplete: StateFlow<Boolean>
    val tradeLocationEvent: SharedFlow<TradeLocationEvent>

    sealed class TradeLocationEvent{
        data class InputAddressComplete(val isInputComplete: Boolean): TradeLocationEvent()

        data class CheckLocation(val latLng: LatLng): TradeLocationEvent()

        data class ToastException(val text: String): TradeLocationEvent()
    }

    fun setTotalPrice(totalPrice: Long)
    fun setTotalMember(totalMember: Long)

    fun setAddress(address: String)

    fun setAddressDetail(addressDetail: String)

    fun setIsInputComplete()
    fun addImages(images: List<Image>)
    fun checkDone()
    fun doneButtonClick()
    fun writeTogether()

    fun fetchGeocoding(query: String)

    fun eventTradeLocation(event: TradeLocationEvent)
}