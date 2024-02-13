package com.umc.ttoklip.presentation.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.data.model.login.LoginRequest
import com.umc.ttoklip.data.repository.signup.SignupRepositoryImpl
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupRepository: SignupRepositoryImpl
) : ViewModel() {

    private val _nickok = MutableStateFlow<Boolean>(false)
    val nickok: StateFlow<Boolean>
        get() = _nickok
    fun nickCheck(nick: String) {
        viewModelScope.launch {
            signupRepository.checkNickname(nick)
                .onSuccess {
                    Log.i("nick check", "성공")
                    _nickok.value=true
                }.onFail {
                    Log.d("nick check", "실패")
                    _nickok.value=false
                }
        }
    }

    private val _nickname = MutableStateFlow<String>("")
    val nickname: StateFlow<String>
        get() = _nickname
    private val _categories = MutableStateFlow<ArrayList<String>>(ArrayList<String>())
    val categories: StateFlow<ArrayList<String>>
        get() = _categories
    private val _profileImage=MutableStateFlow<String>("")
    val profileImage:StateFlow<String>
        get() = _profileImage
    private val _independenctYear=MutableStateFlow<Int>(0)
    val independentYear:StateFlow<Int>
        get() = _independenctYear
    private val _independenctMonth=MutableStateFlow<Int>(0)
    val independentMonth:StateFlow<Int>
        get() = _independenctMonth

    fun saveUserInfo(
        nick: String,
        categories: ArrayList<String>,
        profileImage: String,
        independentYear: Int,
        independentMonth: Int
    ) {
        _nickname.value=nick
        _categories.value=categories
        _profileImage.value=profileImage
        _independenctYear.value=independentYear
        _independenctMonth.value=independentMonth
        TtoklipApplication.prefs.setString("nickname",nick)
        Log.d("닉네임","${nick}")
        Log.d("닉네임","${TtoklipApplication.prefs.getString("nickname", "")}")
    }
}