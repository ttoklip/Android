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
    private var honeyTipPagingObjects =
        listOf(PagingObject(), PagingObject(), PagingObject(), PagingObject())
    private var questionPagingObjects =
        listOf(PagingObject(), PagingObject(), PagingObject(), PagingObject())

    //꿀팁 리스트
    private val _topFiveQuestions = MutableStateFlow<List<HoneyTipMain>>(listOf())
    val topFiveQuestions = _topFiveQuestions.asStateFlow()

    val houseworkHoneyTip = honeyTipPagingObjects[HOUSEWORK].list.asStateFlow()
    val recipeHoneyTip = honeyTipPagingObjects[RECIPE].list.asStateFlow()
    val safeLivingHoneyTip = honeyTipPagingObjects[SAFE_LIVING].list.asStateFlow()
    val welfareHoneyTip = honeyTipPagingObjects[WELFARE_POLICY].list.asStateFlow()

    fun resetHoneyTipList(category: String) {
        Log.d("reset", "reset")
        when (category) {
            "HOUSEWORK" -> honeyTipPagingObjects[HOUSEWORK].resetList()
            "RECIPE" -> honeyTipPagingObjects[RECIPE].resetList()
            "SAFE_LIVING" -> honeyTipPagingObjects[SAFE_LIVING].resetList()
            "WELFARE_POLICY" -> honeyTipPagingObjects[WELFARE_POLICY].resetList()
        }
    }

    fun resetQuestionList(category: String) {
        Log.d("reset", "reset")
        when (category) {
            "HOUSEWORK" -> questionPagingObjects[HOUSEWORK].resetList()
            "RECIPE" -> questionPagingObjects[RECIPE].resetList()
            "SAFE_LIVING" -> questionPagingObjects[SAFE_LIVING].resetList()
            "WELFARE_POLICY" -> questionPagingObjects[WELFARE_POLICY].resetList()
        }
    }

    //질문 리스트
    val houseworkQuestion = questionPagingObjects[HOUSEWORK].list.asStateFlow()
    val recipeQuestion = questionPagingObjects[RECIPE].list.asStateFlow()
    val safeLivingQuestion = questionPagingObjects[SAFE_LIVING].list.asStateFlow()
    val welfareQuestion = questionPagingObjects[WELFARE_POLICY].list.asStateFlow()

    fun getHoneyTipMain() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getHoneyTipMain().onSuccess {
                //Top 5
                _topFiveQuestions.emit(it.topFiveQuestions)

                honeyTipPagingObjects[HOUSEWORK].initList(it.honeyTipCategory.housework)
                honeyTipPagingObjects[RECIPE].initList(it.honeyTipCategory.cooking)
                honeyTipPagingObjects[SAFE_LIVING].initList(it.honeyTipCategory.safeLiving)
                honeyTipPagingObjects[WELFARE_POLICY].initList(it.honeyTipCategory.welfarePolicy)

                questionPagingObjects[HOUSEWORK].initList(it.questionCategory.housework)
                questionPagingObjects[RECIPE].initList(it.questionCategory.cooking)
                questionPagingObjects[SAFE_LIVING].initList(it.questionCategory.safeLiving)
                questionPagingObjects[WELFARE_POLICY].initList(it.questionCategory.welfarePolicy)

                Log.d("HoneyTipMain api", it.toString())
            }
        }
    }

    fun getHoneyTipPage(category: String) {
        val index = when (category) {
            "HOUSEWORK" -> HOUSEWORK
            "RECIPE" -> RECIPE
            "SAFE_LIVING" -> SAFE_LIVING
            else -> WELFARE_POLICY
        }
        getHoneyTipCategoryPage(category, honeyTipPagingObjects[index])
    }

    private fun getHoneyTipCategoryPage(category: String, paging: PagingObject) {
        with(paging) {
            if (!isEnd.value) {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        repository.getHoneyTipByCategory(category, page.value)
                            .onSuccess {
                                list.emit(list.value + it.data)
                                page.value = page.value + 1
                                isEnd.value = it.isLast
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

    fun getQuestionPage(category: String) {
        val index = when (category) {
            "HOUSEWORK" -> HOUSEWORK
            "RECIPE" -> RECIPE
            "SAFE_LIVING" -> SAFE_LIVING
            else -> WELFARE_POLICY
        }
        getQuestionCategoryPage(category, questionPagingObjects[index])
    }

    private fun getQuestionCategoryPage(category: String, paging: PagingObject) {
        with(paging) {
            if (!isEnd.value) {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        repository.getQuestionByCategory(category, page.value)
                            .onSuccess {
                                list.emit(list.value + it.data)
                                page.value = page.value + 1
                                isEnd.value = it.isLast
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

    data class PagingObject(
        val isEnd: MutableStateFlow<Boolean>,
        var prevList: List<HoneyTipMain>,
        val list: MutableStateFlow<List<HoneyTipMain>>,
        val page: MutableStateFlow<Int>
    ) {
        constructor() : this(
            MutableStateFlow(false),
            emptyList(),
            MutableStateFlow(emptyList()),
            MutableStateFlow(1)
        )

        fun initList(list: List<HoneyTipMain>) {
            this.list.value = list
            prevList = list
        }

        fun resetList() {
            this.list.value = prevList
            isEnd.value = false
            page.value = 1
        }
    }

    companion object {
        const val HOUSEWORK = 0
        const val RECIPE = 1
        const val SAFE_LIVING = 2
        const val WELFARE_POLICY = 3
    }
}