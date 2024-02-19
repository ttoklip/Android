package com.umc.ttoklip.presentation.honeytip

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.model.honeytip.HoneyTipMain
import com.umc.ttoklip.data.model.honeytip.HoneyTipPagingResponse
import com.umc.ttoklip.data.repository.honeytip.HoneyTipRepositoryImpl
import com.umc.ttoklip.module.onException
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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

    private val _honeyTipPaging = MutableStateFlow(HoneyTipPagingResponse())
    val honeyTipPaging = _honeyTipPaging.asStateFlow()

    private val _isLast = MutableStateFlow(false)
    val isLast = _isLast.asStateFlow()


    private val _honeyTipMainEvent = MutableSharedFlow<Boolean>()
    val honeyTipMainEvent = _honeyTipMainEvent.asSharedFlow()

    //꿀팁 리스트
    private val _topFiveQuestions = MutableStateFlow<List<HoneyTipMain>>(listOf())
    val topFiveQuestions = _topFiveQuestions.asStateFlow()

    private val _houseworkHoneyTip = MutableStateFlow<List<HoneyTipMain>>(listOf())
    val houseworkHoneyTip = _houseworkHoneyTip.asStateFlow()

    private val _recipeHoneyTip = MutableStateFlow<List<HoneyTipMain>>(listOf())
    val recipeHoneyTip = _recipeHoneyTip.asStateFlow()

    private val _safeLivingHoneyTip = MutableStateFlow<List<HoneyTipMain>>(listOf())
    val safeLivingHoneyTip = _safeLivingHoneyTip.asStateFlow()

    private val _welfareHoneyTip = MutableStateFlow<List<HoneyTipMain>>(listOf())
    val welfareHoneyTip = _welfareHoneyTip.asStateFlow()

    val isHouseEnd = MutableStateFlow<Boolean>(false)
    val isRecipeEnd = MutableStateFlow<Boolean>(false)
    val isSafeEnd = MutableStateFlow<Boolean>(false)
    val isWelfEnd = MutableStateFlow<Boolean>(false)

    val housePage = MutableStateFlow<Int>(1)
    val recipePage = MutableStateFlow<Int>(1)
    val safePage = MutableStateFlow<Int>(1)
    val welfPage = MutableStateFlow<Int>(1)

    fun resetHouseHoneyTipList(){
        Log.d("reset", "reset")
        _houseworkHoneyTip.value = listOf()
    }

    //질문 리스트
    private val _houseworkQuestion = MutableStateFlow<List<HoneyTipMain>>(listOf())
    val houseworkQuestion = _houseworkQuestion.asStateFlow()

    private val _recipeQuestion = MutableStateFlow<List<HoneyTipMain>>(listOf())
    val recipeQuestion = _recipeQuestion.asStateFlow()

    private val _safeLivingQuestion = MutableStateFlow<List<HoneyTipMain>>(listOf())
    val safeLivingQuestion = _safeLivingQuestion.asStateFlow()

    private val _welfareQuestion = MutableStateFlow<List<HoneyTipMain>>(listOf())
    val welfareQuestion = _welfareQuestion.asStateFlow()

    fun getHoneyTipMain() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getHoneyTipMain().onSuccess {
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

    fun getHouseHoneyTipPage() {
        if (!isHouseEnd.value) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    repository.getHoneyTipByCategory("HOUSEWORK", housePage.value)
                        .onSuccess {
                            _houseworkHoneyTip.emit(_houseworkHoneyTip.value + it.data)
                            Log.d("housework", _houseworkHoneyTip.value.toString())
                            Log.d("housework", _houseworkHoneyTip.value.size.toString())
                            housePage.value = housePage.value + 1
                            isHouseEnd.value = it.isLast
                        }.onFail {

                        }.onException {
                            throw it
                        }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d("예외", "$e")
                }
            }
        }
    }

    fun getRecipeHoneyTipPage() {
        if (!isRecipeEnd.value) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    repository.getHoneyTipByCategory("RECIPE", recipePage.value)
                        .onSuccess {
                            _recipeHoneyTip.emit(_recipeHoneyTip.value + it.data)
                            Log.d("housework", _houseworkHoneyTip.value.toString())
                            recipePage.value = recipePage.value + 1
                            isRecipeEnd.value = it.isLast
                        }.onFail {

                        }.onException {
                            throw it
                        }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d("예외", "$e")
                }
            }
        }
    }

    fun getSafeLivingHoneyTipPage() {
        if (!isSafeEnd.value) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    repository.getHoneyTipByCategory("SAFE_LIVING", safePage.value)
                        .onSuccess {
                            _safeLivingHoneyTip.emit(_safeLivingHoneyTip.value + it.data)
                            Log.d("housework", _safeLivingHoneyTip.value.toString())
                            safePage.value = safePage.value + 1
                            isSafeEnd.value = it.isLast
                        }.onFail {

                        }.onException {
                            throw it
                        }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d("예외", "$e")
                }
            }
        }
    }

    fun getWelFareHoneyTipPage() {
        if (!isWelfEnd.value) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    repository.getHoneyTipByCategory("WELFARE_POLICY", welfPage.value)
                        .onSuccess {
                            _welfareHoneyTip.emit(_welfareHoneyTip.value + it.data)
                            Log.d("housework", _houseworkHoneyTip.value.toString())
                            welfPage.value = welfPage.value + 1
                            isWelfEnd.value = it.isLast
                        }.onFail {

                        }.onException {
                            throw it
                        }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d("예외", "$e")
                }
            }
        }
    }
}