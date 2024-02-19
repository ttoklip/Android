package com.umc.ttoklip.presentation.signup.fragments

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.R
import com.umc.ttoklip.data.repository.signup.TermRepositoryImpl
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TermViewModel @Inject constructor(
    private val termRepository: TermRepositoryImpl,
) : ViewModel() {

    private val _termDatas= MutableStateFlow<ArrayList<Term>>(ArrayList())
    val termDatas: StateFlow<ArrayList<Term>>
        get() = _termDatas

    private val _nextok= MutableStateFlow<Boolean>(false)
    val nextok:StateFlow<Boolean>
        get() = _nextok
    private val _allCheck= MutableStateFlow<Boolean>(false)
    val allCheck:StateFlow<Boolean>
        get() = _allCheck

    fun nextcheck(check:Boolean){
        _nextok.value=check
    }
    fun allcheck(check:Boolean){
        _allCheck.value=check
    }
    fun setTermsCheck(check:Boolean){
        for(term in _termDatas.value){
            _termDatas.value[term.termId-1].check=check
        }
    }
    fun setTermCheck(position:Int,check:Boolean){
        _termDatas.value[position].check=check
    }
    fun getTerm() {
        viewModelScope.launch {
            termRepository.getTerm(0)
                .onSuccess {
                    for(term in it.terms){
                        _termDatas.value.add(Term(term.termId,term.title,term.content))
                    }
                    _termCount.value=it.totalElements
                    Log.i("TERM","term 불러오기 성공")
                }.onFail {
                    Log.d("TERM","term 불러오기 실패")
                }
        }
    }

    private val _termCount=MutableStateFlow<Int>(0)
    val termCount:StateFlow<Int>
        get() = _termCount

    data class Term(
        val termId: Int,
        val title: String,
        val content: String,
        var check: Boolean = false
    )
}