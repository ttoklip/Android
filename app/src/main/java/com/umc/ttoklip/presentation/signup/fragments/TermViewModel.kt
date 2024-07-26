package com.umc.ttoklip.presentation.signup.fragments

import android.app.Application
import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.AndroidViewModel
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
    private val termRepository: TermRepositoryImpl, application: Application
) : AndroidViewModel(application) {

    val termDatas= MutableStateFlow<ArrayList<Term>>(ArrayList())

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
        for(term in termDatas.value){
            termDatas.value[term.termId-1].check=check
        }
    }
    fun setTermCheck(position:Int,check:Boolean){
        termDatas.value[position].check=check
    }
    fun getTerm() {
//        val termName=getAllString(resource,R.array.term_name)
//        val termContent=getAllString(resource,R.array.term_content)
//        for(i in 0 until termName.size){
//            termDatas.value.add(Term(i,termName[i],termContent[i],false))
//        }
        viewModelScope.launch {
            termRepository.getTerm()
                .onSuccess {
//                    for(term in it){
                    termDatas.value.add(Term(it.agreeTermsOfService.termId,it.agreeTermsOfService.termTitle,it.agreeTermsOfService.termContent))
                    termDatas.value.add(Term(it.agreePrivacyPolicy.termId,it.agreePrivacyPolicy.termTitle,it.agreePrivacyPolicy.termContent))
                    termDatas.value.add(Term(it.agreeLocationService.termId,it.agreeLocationService.termTitle,it.agreeLocationService.termContent))
//                    }
                    Log.i("TERM","term 불러오기 성공")
                }.onFail {
                    Log.d("TERM","term 불러오기 실패")
                }
        }
    }
    private fun getAllString(resource: Resources,resId: Int): Array<String> {
        return resource.getStringArray(resId)
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