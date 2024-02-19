package com.umc.ttoklip.presentation.hometown

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.model.town.CreateTogethersRequest
import com.umc.ttoklip.data.repository.town.WriteTogetherRepository
import com.umc.ttoklip.module.onError
import com.umc.ttoklip.module.onSuccess
import com.umc.ttoklip.presentation.honeytip.adapter.Image
import com.umc.ttoklip.util.WriteHoneyTipUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteTogetherViewModelImpl @Inject constructor(
    private val repository: WriteTogetherRepository,
    @ApplicationContext private val context: Context
) :
    ViewModel(), WriteTogetherViewModel {
    val _title: MutableStateFlow<String> = MutableStateFlow<String>("")
    override val title: StateFlow<String>
        get() = _title

    private val _totalPrice: MutableStateFlow<Long> = MutableStateFlow<Long>(0)
    override val totalPrice: StateFlow<Long>
        get() = _totalPrice

    private val _totalMember: MutableStateFlow<Long> = MutableStateFlow<Long>(0)
    override val totalMember: StateFlow<Long>
        get() = _totalMember

    val _dealPlace: MutableStateFlow<String> = MutableStateFlow<String>("")
    override val dealPlace: StateFlow<String>
        get() = _dealPlace

    val _openLink: MutableStateFlow<String> = MutableStateFlow<String>("")
    override val openLink: StateFlow<String>
        get() = _openLink

    val _content: MutableStateFlow<String> = MutableStateFlow<String>("")
    override val content: StateFlow<String>
        get() = _content

    val _extraUrl: MutableStateFlow<String> = MutableStateFlow<String>("")
    override val extraUrl: StateFlow<String>
        get() = _extraUrl

    val _doneButtonActivated: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    override val doneButtonActivated: StateFlow<Boolean>
        get() = _doneButtonActivated

    val _doneWriteTogether: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    override val doneWriteTogether: StateFlow<Boolean>
        get() = _doneWriteTogether

    private val _closePage: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val closePage: StateFlow<Boolean>
        get() = _closePage

    private val _images: MutableStateFlow<List<Image>> = MutableStateFlow(mutableListOf())
    override val images: StateFlow<List<Image>>
        get() = _images


    override fun setTotalPrice(totalPrice: Long) {
        _totalPrice.value = totalPrice
    }

    override fun setTotalMember(totalMember: Long) {
        _totalMember.value = totalMember
    }


    override fun addImages(images: List<Image>) {
        val combined = _images.value + images
        _images.value = combined
    }


    override fun checkDone() {
        _doneButtonActivated.value =
            title.value.isNotBlank() && openLink.value.isNotBlank() && content.value.isNotBlank() && totalPrice.value > 0 && totalMember.value > 0
    }

    override fun doneButtonClick() {
        _doneWriteTogether.value = true
    }

    override fun writeTogether() {
        val request = CreateTogethersRequest(
            title = title.value,
            content = content.value,
            totalPrice = totalPrice.value,
            location = dealPlace.value,
            chatUrl = openLink.value,
            party = totalMember.value,
            itemUrls = listOf(extraUrl.value),
            images = WriteHoneyTipUtil(context).convertUriListToMultiBody(images.value.map { it.uri })
                .toList()
        )
        Log.d("request", request.toString())
        viewModelScope.launch {
            repository.createTogether(request).onSuccess {
                _closePage.value = true
            }.onError {
                Log.d("writetogethererror", it.toString())
            }
        }
    }

}