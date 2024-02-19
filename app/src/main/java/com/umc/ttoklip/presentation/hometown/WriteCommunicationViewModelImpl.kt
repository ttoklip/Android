package com.umc.ttoklip.presentation.hometown

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.model.town.CreateCommunicationsRequest
import com.umc.ttoklip.data.repository.town.WriteCommsRepository
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
class WriteCommunicationViewModelImpl @Inject constructor(
    private val repository: WriteCommsRepository,
    @ApplicationContext private val context: Context
) :
    ViewModel(), WriteCommunicationViewModel {
    val _title: MutableStateFlow<String> = MutableStateFlow<String>("")
    override val title: StateFlow<String>
        get() = _title

    val _content: MutableStateFlow<String> = MutableStateFlow<String>("")
    override val content: StateFlow<String>
        get() = _content

    private val _images: MutableStateFlow<List<Image>> = MutableStateFlow(mutableListOf())
    override val images: StateFlow<List<Image>>
        get() = _images

    private val _closePage: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val closePage: StateFlow<Boolean>
        get() = _closePage


    val _doneButtonActivated: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    override val doneButtonActivated: StateFlow<Boolean>
        get() = _doneButtonActivated

    override fun addImages(images: List<Image>) {
        val combined = _images.value + images
        _images.value = combined
    }

    override fun checkDone() {
        _doneButtonActivated.value =
            title.value.isNotBlank() && content.value.isNotBlank()
    }

    override fun doneButtonClick() {
        viewModelScope.launch {
            repository.createComms(
                body = CreateCommunicationsRequest(
                    title = title.value,
                    content = content.value,
                    images = WriteHoneyTipUtil(context).convertUriToMultiBody(images.value.map { it.uri })
                        .toList()
                )
            ).onSuccess {
                _closePage.value = true
            }.onError {
                Log.d("writetogethererror", it.toString())
            }
        }
    }

}