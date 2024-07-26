package com.umc.ttoklip.presentation.honeytip.write

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.repository.honeytip.HoneyTipRepositoryImpl
import com.umc.ttoklip.module.onError
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class WriteHoneyTipViewModel @Inject constructor(
    private val repository: HoneyTipRepositoryImpl
) : ViewModel() {

    private val _writeDoneEvent = MutableSharedFlow<WriteDoneEvent>()
    val writeDoneEvent = _writeDoneEvent.asSharedFlow()

    sealed class WriteDoneEvent {
        data class WriteDoneHoneyTip(val postId: Int) : WriteDoneEvent()
        data class WriteDoneQuestion(val postId: Int) : WriteDoneEvent()
    }

    private val _isTitleNull = MutableStateFlow(true)
    private val _isContentNull = MutableStateFlow(true)

    private val _content = MutableStateFlow("")
    val content = _content.asStateFlow()

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _isEdit = MutableStateFlow<Boolean>(false)
    val isEdit: StateFlow<Boolean> get() = _isEdit

    private val _isWriteDoneBtnEnable = MutableStateFlow(false)
    val isWriteDoneBtnEnable: StateFlow<Boolean> get() = _isWriteDoneBtnEnable


    fun setTitle(isTitleNull: Boolean) {
        _isTitleNull.value = isTitleNull
        _isWriteDoneBtnEnable.value = (_isTitleNull.value.not() && _isContentNull.value.not()) or isEdit.value
    }

    fun setContent(isContentNull: Boolean) {
        _isContentNull.value = isContentNull
        _isWriteDoneBtnEnable.value = (_isTitleNull.value.not() && _isContentNull.value.not()) or isEdit.value
    }

    fun setIsWriteDoneBtnEnable(isWriteDoneEnable: Boolean){
        _isWriteDoneBtnEnable.value = isWriteDoneEnable
    }

    fun setIsEdit(isEdit: Boolean){
        _isEdit.value = isEdit
    }

    private fun convertStringToTextPlain(string: String): RequestBody {
        return string.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    private fun createRequestBodyFromList(list: List<Int>): RequestBody {
        val listString = list.joinToString(",") // List를 쉼표로 구분된 문자열로 변환
        return RequestBody.create("text/plain".toMediaTypeOrNull(), listString)
    }

    private fun event(event: WriteDoneEvent) {
        viewModelScope.launch {
            _writeDoneEvent.emit(event)
        }
    }

    fun createHoneyTip(
        title: String,
        content: String,
        category: String,
        images: List<MultipartBody.Part?>,
        url: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.createHoneyTip(
                convertStringToTextPlain(title),
                convertStringToTextPlain(content),
                convertStringToTextPlain(category),
                images,
                convertStringToTextPlain(url)
            ).onSuccess {
                val postId = it.message.replace(("[^\\d]").toRegex(), "").toInt()
                event(WriteDoneEvent.WriteDoneHoneyTip(postId))
                Log.d("honey tip api test", it.message)
            }
        }
    }

    fun editHoneyTip(
        honeyTipId: Int,
        title: String,
        content: String,
        category: String,
        deleteImageIds: List<Int>,
        addImages: List<MultipartBody.Part?>,
        url: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.editHoneyTip(
                honeyTipId,
                convertStringToTextPlain(title),
                convertStringToTextPlain(content),
                convertStringToTextPlain(category),
                createRequestBodyFromList(deleteImageIds),
                addImages,
                convertStringToTextPlain(url)
            ).onSuccess {
                val postId = it.message.replace(("[^\\d]").toRegex(), "").toInt()
                event(WriteDoneEvent.WriteDoneHoneyTip(postId))
                Log.d("edit honey tip api test", it.message)
            }.onError {
                Log.d("error", it.stackTraceToString())
            }
        }
    }

    fun createQuestion(
        title: String,
        content: String,
        category: String,
        images: List<MultipartBody.Part?>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.createQuestion(
                convertStringToTextPlain(title),
                convertStringToTextPlain(content),
                convertStringToTextPlain(category),
                images
            ).onSuccess {
                val postId = it.message.replace(("[^\\d]").toRegex(), "").toInt()
                event(WriteDoneEvent.WriteDoneQuestion(postId))
            }
        }
    }
}