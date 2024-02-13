package com.umc.ttoklip.presentation.honeytip

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.model.honeytip.HoneyTipMainResponse
import com.umc.ttoklip.data.model.honeytip.HoneyTipResponse
import com.umc.ttoklip.data.model.honeytip.InquireHoneyTipResponse
import com.umc.ttoklip.data.repository.honeytip.HoneyTipRepositoryImpl
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class HoneyTipViewModel @Inject constructor(
    private val repository: HoneyTipRepositoryImpl
) : ViewModel() {

    val boardLiveData: LiveData<String> by lazy { _boardLiveData }
    private val _boardLiveData by lazy { MutableLiveData<String>("꿀팁 공유") }

    fun setBoardLiveData(board: String) {
        _boardLiveData.value = board
    }

    val isTitleNull: LiveData<Boolean> by lazy { _isTitleNull }
    private val _isTitleNull by lazy { MutableLiveData<Boolean>(true) }

    val isBodyNull: LiveData<Boolean> by lazy { _isBodyNull }
    private val _isBodyNull by lazy { MutableLiveData<Boolean>(true) }

    private val _writeDoneEvent = MutableSharedFlow<String>()
    val writeDoneEvent = _writeDoneEvent.asSharedFlow()

    private val _honeyTip = MutableSharedFlow<InquireHoneyTipResponse>()
    val honeyTip = _honeyTip.asSharedFlow()

    private val _honeyTipMainEvent = MutableSharedFlow<Boolean>()
    val honeyTipMainEvent = _honeyTipMainEvent.asSharedFlow()

    //꿀팁 리스트
    private val _topFiveQuestions = MutableStateFlow<List<HoneyTipResponse>>(listOf())
    val topFiveQuestions = _topFiveQuestions.asStateFlow()

    private val _houseworkHoneyTip = MutableStateFlow<List<HoneyTipResponse>>(listOf())
    val houseworkHoneyTip = _houseworkHoneyTip.asStateFlow()

    private val _recipeHoneyTip = MutableStateFlow<List<HoneyTipResponse>>(listOf())
    val recipeHoneyTip = _recipeHoneyTip.asStateFlow()

    private val _safeLivingHoneyTip = MutableStateFlow<List<HoneyTipResponse>>(listOf())
    val safeLivingHoneyTip = _safeLivingHoneyTip.asStateFlow()

    private val _welfareHoneyTip = MutableStateFlow<List<HoneyTipResponse>>(listOf())
    val welfareHoneyTip = _welfareHoneyTip.asStateFlow()

    //질문 리스트
    private val _houseworkQuestion = MutableStateFlow<List<HoneyTipResponse>>(listOf())
    val houseworkQuestion = _houseworkQuestion.asStateFlow()

    private val _recipeQuestion = MutableStateFlow<List<HoneyTipResponse>>(listOf())
    val recipeQuestion = _recipeQuestion.asStateFlow()

    private val _safeLivingQuestion = MutableStateFlow<List<HoneyTipResponse>>(listOf())
    val safeLivingQuestion = _safeLivingQuestion.asStateFlow()

    private val _welfareQuestion = MutableStateFlow<List<HoneyTipResponse>>(listOf())
    val welfareQuestion = _welfareQuestion.asStateFlow()

    fun setTitle(boolean: Boolean) {
        _isTitleNull.value = boolean
    }

    fun setBody(boolean: Boolean) {
        _isBodyNull.value = boolean
    }

    private fun convertStringToTextPlain(string: String): RequestBody {
        return string.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    fun createHoneyTip(
        title: String,
        content: String,
        category: String,
        images: Array<MultipartBody.Part>,
        uri: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.createHoneyTip(
                convertStringToTextPlain(title),
                convertStringToTextPlain(content),
                convertStringToTextPlain(category),
                images,
                convertStringToTextPlain(uri)
            ).onSuccess {
                withContext(Dispatchers.Main) {
                    _writeDoneEvent.emit("true")
                    _honeyTipMainEvent.emit(true)
                    Log.d("honey tip api test", it.message)
                }
            }
        }
    }

    fun createQuestion(
        title: String,
        content: String,
        category: String,
        images: Array<MultipartBody.Part>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.createQuestion(
                convertStringToTextPlain(title),
                convertStringToTextPlain(content),
                convertStringToTextPlain(category),
                images
            ).onSuccess {
                withContext(Dispatchers.Main) {
                    Log.d("question", it.message)
                }
            }
        }
    }

    fun getHoneyTipMain() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getHoneyTipMain().onSuccess {
                withContext(Dispatchers.Main) {
                    //Top 5
                    _topFiveQuestions.emit(it.topFiveQuestions)

                    //꿀팁
                    _houseworkHoneyTip.emit(it.honeyTipCategory.housework)
                    _recipeHoneyTip.emit(it.honeyTipCategory.cooking)
                    _safeLivingHoneyTip.emit(it.honeyTipCategory.safeLiving)
                    _welfareHoneyTip.emit(it.honeyTipCategory.welfarePolicy)

                    //질문
                    _houseworkQuestion.emit(it.questionCategory.housework)
                    _recipeQuestion.emit(it.questionCategory.cooking)
                    _safeLivingQuestion.emit(it.questionCategory.safeLiving)
                    _welfareQuestion.emit(it.questionCategory.welfarePolicy)
                    _honeyTipMainEvent.emit(true)
                    Log.d("HoneyTipMain api", it.toString())
                }
            }
        }
    }

    fun inquireHoneyTip(honeyTipId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.inquireHoneyTip(honeyTipId).onSuccess {
                withContext(Dispatchers.Main) {
                    Log.d("inquire HoneyTip api", it.toString())
                    _honeyTip.emit(it)
                }
            }
        }
    }
}