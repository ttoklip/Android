package com.umc.ttoklip.presentation.honeytip

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.model.honeytip.HoneyTipMain
import com.umc.ttoklip.data.repository.honeytip.HoneyTipRepositoryImpl
import com.umc.ttoklip.module.onException
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import com.umc.ttoklip.presentation.honeytip.write.WriteHoneyTipActivity
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
    private var prevHouseHoneyTipList: List<HoneyTipMain> = listOf()

    val boardLiveData: LiveData<String> by lazy { _boardLiveData }
    private val _boardLiveData by lazy { MutableLiveData<String>("꿀팁 공유") }

    fun setBoardLiveData(board: String) {
        _boardLiveData.value = board
    }

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

    private val isHouseHoneyTipEnd = MutableStateFlow<Boolean>(false)
    private val isRecipeHoneyTipEnd = MutableStateFlow<Boolean>(false)
    private val isSafeHoneyTipEnd = MutableStateFlow<Boolean>(false)
    private val isWelfHoneyTipEnd = MutableStateFlow<Boolean>(false)

    private val houseHoneyTipPage = MutableStateFlow<Int>(1)
    private val recipeHoneyTipPage = MutableStateFlow<Int>(1)
    private val safeHoneyTipPage = MutableStateFlow<Int>(1)
    private val welfHoneyTipPage = MutableStateFlow<Int>(1)

    private val isHouseQuestionEnd = MutableStateFlow<Boolean>(false)
    private val isRecipeQuestionEnd = MutableStateFlow<Boolean>(false)
    private val isSafeQuestionEnd = MutableStateFlow<Boolean>(false)
    private val isWelfQuestionEnd = MutableStateFlow<Boolean>(false)

    private val houseQuestionPage = MutableStateFlow<Int>(1)
    private val recipeQuestionPage = MutableStateFlow<Int>(1)
    private val safeQuestionPage = MutableStateFlow<Int>(1)
    private val welfQuestionPage = MutableStateFlow<Int>(1)

    fun resetHoneyTipList(category: String) {
        //_houseworkHoneyTip.value = prevHouseHoneyTipList
        Log.d("reset", "reset")
        when (category) {
            WriteHoneyTipActivity.Category.HOUSEWORK.toString() -> {
                _houseworkHoneyTip.value = emptyList()
                isHouseHoneyTipEnd.value = false
                houseHoneyTipPage.value = 1
            }

            WriteHoneyTipActivity.Category.RECIPE.toString() -> {
                _recipeHoneyTip.value = emptyList()
                isRecipeHoneyTipEnd.value = false
                recipeHoneyTipPage.value = 1
            }

            WriteHoneyTipActivity.Category.SAFE_LIVING.toString() -> {
                _safeLivingHoneyTip.value = emptyList()
                isSafeHoneyTipEnd.value = false
                safeHoneyTipPage.value = 1
            }

            WriteHoneyTipActivity.Category.WELFARE_POLICY.toString() -> {
                _welfareHoneyTip.value = emptyList()
                isWelfHoneyTipEnd.value = false
                welfHoneyTipPage.value = 1
            }
        }
    }

    fun resetQuestionList(category: String) {
        //_houseworkHoneyTip.value = prevHouseHoneyTipList
        Log.d("reset", "reset")
        when (category) {
            WriteHoneyTipActivity.Category.HOUSEWORK.toString() -> {
                _houseworkQuestion.value = emptyList()
                isHouseQuestionEnd.value = false
                houseQuestionPage.value = 1
            }

            WriteHoneyTipActivity.Category.RECIPE.toString() -> {
                _recipeQuestion.value = emptyList()
                isRecipeQuestionEnd.value = false
                recipeQuestionPage.value = 1
            }

            WriteHoneyTipActivity.Category.SAFE_LIVING.toString() -> {
                _safeLivingQuestion.value = emptyList()
                isSafeQuestionEnd.value = false
                safeQuestionPage.value = 1
            }

            WriteHoneyTipActivity.Category.WELFARE_POLICY.toString() -> {
                _welfareQuestion.value = emptyList()
                isWelfQuestionEnd.value = false
                welfQuestionPage.value = 1
            }
        }
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
                prevHouseHoneyTipList = it.honeyTipCategory.housework
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
        if (!isHouseHoneyTipEnd.value) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    repository.getHoneyTipByCategory("HOUSEWORK", houseHoneyTipPage.value)
                        .onSuccess {
                            _houseworkHoneyTip.emit(_houseworkHoneyTip.value + it.data)
                            Log.d("housework", _houseworkHoneyTip.value.toString())
                            Log.d("housework", _houseworkHoneyTip.value.size.toString())
                            houseHoneyTipPage.value = houseHoneyTipPage.value + 1
                            isHouseHoneyTipEnd.value = it.isLast
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
        if (!isRecipeHoneyTipEnd.value) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    repository.getHoneyTipByCategory("RECIPE", recipeHoneyTipPage.value)
                        .onSuccess {
                            _recipeHoneyTip.emit(_recipeHoneyTip.value + it.data)
                            Log.d("housework", _houseworkHoneyTip.value.toString())
                            recipeHoneyTipPage.value = recipeHoneyTipPage.value + 1
                            isRecipeHoneyTipEnd.value = it.isLast
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
        if (!isSafeHoneyTipEnd.value) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    repository.getHoneyTipByCategory("SAFE_LIVING", safeHoneyTipPage.value)
                        .onSuccess {
                            _safeLivingHoneyTip.emit(_safeLivingHoneyTip.value + it.data)
                            Log.d("housework", _safeLivingHoneyTip.value.toString())
                            safeHoneyTipPage.value = safeHoneyTipPage.value + 1
                            isSafeHoneyTipEnd.value = it.isLast
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
        if (!isWelfHoneyTipEnd.value) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    repository.getHoneyTipByCategory("WELFARE_POLICY", welfHoneyTipPage.value)
                        .onSuccess {
                            _welfareHoneyTip.emit(_welfareHoneyTip.value + it.data)
                            Log.d("housework", _houseworkHoneyTip.value.toString())
                            welfHoneyTipPage.value = welfHoneyTipPage.value + 1
                            isWelfHoneyTipEnd.value = it.isLast
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

    fun getHouseQuestionPage() {
        if (!isHouseQuestionEnd.value) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    repository.getQuestionByCategory("HOUSEWORK", houseQuestionPage.value)
                        .onSuccess {
                            _houseworkQuestion.emit(_houseworkQuestion.value + it.data)
                            Log.d("housework", _houseworkQuestion.value.toString())
                            Log.d("housework", _houseworkQuestion.value.size.toString())
                            houseQuestionPage.value = houseQuestionPage.value + 1
                            isHouseQuestionEnd.value = it.isLast
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

    fun getRecipeQuestionPage() {
        if (!isRecipeQuestionEnd.value) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    repository.getQuestionByCategory("RECIPE", recipeQuestionPage.value)
                        .onSuccess {
                            _recipeQuestion.emit(_recipeQuestion.value + it.data)
                            Log.d("housework", _recipeQuestion.value.toString())
                            Log.d("housework", _recipeQuestion.value.size.toString())
                            recipeQuestionPage.value = recipeQuestionPage.value + 1
                            isRecipeQuestionEnd.value = it.isLast
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

    fun getSafeQuestionPage() {
        if (!isSafeQuestionEnd.value) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    repository.getQuestionByCategory("SAFE_LIVING", safeQuestionPage.value)
                        .onSuccess {
                            _safeLivingQuestion.emit(_safeLivingQuestion.value + it.data)
                            Log.d("housework", _safeLivingQuestion.value.toString())
                            Log.d("housework", _safeLivingQuestion.value.size.toString())
                            safeQuestionPage.value = safeQuestionPage.value + 1
                            isSafeQuestionEnd.value = it.isLast
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

    fun getWelQuestionPage() {
        if (!isWelfQuestionEnd.value) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    repository.getQuestionByCategory("WELFARE_POLICY", welfQuestionPage.value)
                        .onSuccess {
                            _welfareQuestion.emit(_welfareQuestion.value + it.data)
                            Log.d("housework", _welfareQuestion.value.toString())
                            Log.d("housework", _welfareQuestion.value.size.toString())
                            welfQuestionPage.value = welfQuestionPage.value + 1
                            isWelfQuestionEnd.value = it.isLast
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