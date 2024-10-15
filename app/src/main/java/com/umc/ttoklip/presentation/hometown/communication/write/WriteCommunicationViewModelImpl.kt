package com.umc.ttoklip.presentation.hometown.communication.write

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.R
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.data.model.town.CreateCommunicationsRequest
import com.umc.ttoklip.data.model.town.EditCommunication
import com.umc.ttoklip.data.repository.town.WriteCommsRepository
import com.umc.ttoklip.module.onError
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import com.umc.ttoklip.presentation.honeytip.adapter.Image
import com.umc.ttoklip.util.WriteHoneyTipUtil
import com.umc.ttoklip.util.convertStringToTextPlain
import com.umc.ttoklip.util.createRequestBodyFromList
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
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

    private val _postId = MutableStateFlow(0L)
    override val postId: StateFlow<Long>
        get() = _postId

    private val _isEditDone = MutableSharedFlow<Boolean>()
    override val isEditDone: SharedFlow<Boolean>
        get() = _isEditDone

    private val _closePage: MutableStateFlow<Long> = MutableStateFlow(0L)
    override val closePage: StateFlow<Long>
        get() = _closePage


    val _doneButtonActivated: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    override val doneButtonActivated: StateFlow<Boolean>
        get() = _doneButtonActivated

    private val _includeSwear = MutableSharedFlow<String>()
    override val includeSwear: SharedFlow<String>
        get() = _includeSwear

    override fun addImages(images: List<Image>) {
        val combined = _images.value + images
        _images.value = combined
    }

    override fun checkDone() {
        _doneButtonActivated.value =
            title.value.isNotBlank() && content.value.isNotBlank()
    }

    override fun doneButtonClick(images: List<MultipartBody.Part?>) {
        viewModelScope.launch {
            repository.createComms(
                body = CreateCommunicationsRequest(
                    title = title.value,
                    content = content.value,
                    images = images
                )
            ).onSuccess {
                _closePage.value = it.message.replace(("[^\\d]").toRegex(), "").toLong()
            }.onError {
                Log.d("writetogethererror", it.toString())
            }.onFail { message ->
//                _includeSwear.emit(message)
                _includeSwear.emit(TtoklipApplication.getString(R.string.post_fail))
            }
        }

    }

    override fun setTitle(title: String) {
        _title.value = title
    }

    override fun setBody(body: String) {
        _content.value = body
    }

    override fun patchCommunication(
        title: String,
        content: String,
        deleteImageIds: List<Int>,
        addImages: List<MultipartBody.Part?>,
        url: String
    ) {
        viewModelScope.launch {
            repository.patchComms(
                postId.value,
                title.convertStringToTextPlain(),
                content.convertStringToTextPlain(),
                deleteImageIds.createRequestBodyFromList(),
                addImages,
                url.convertStringToTextPlain()
            )
                .onSuccess {
                    _isEditDone.emit(true)
                }.onFail { message ->
                    _includeSwear.emit(message)
                }
        }
    }

    override fun setPostId(postId: Long) {
        _postId.value = postId
    }

    override fun setImage(image: List<Image>) {
        _images.value = image
    }
}