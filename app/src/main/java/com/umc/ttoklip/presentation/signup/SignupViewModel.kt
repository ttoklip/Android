package com.umc.ttoklip.presentation.signup

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.data.model.signup.SignupRequest
import com.umc.ttoklip.data.repository.signup.SignupRepositoryImpl
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupRepository: SignupRepositoryImpl
) : ViewModel() {

    private val _nickok = MutableStateFlow<Boolean>(false)
    val nickok: StateFlow<Boolean>
        get() = _nickok
    private var _independentCareerok = MutableStateFlow<Boolean>(false)
    val independentCareerok: StateFlow<Boolean>
        get() = _independentCareerok
    private var _interestok = MutableStateFlow<Boolean>(false)
    val interestok: StateFlow<Boolean>
        get() = _interestok

    fun nickCheck(nick: String) {
        viewModelScope.launch {
            signupRepository.checkNickname(nick)
                .onSuccess {
                    Log.i("nick check", "성공")
                    _nickok.value = true
                }.onFail {
                    Log.d("nick check", "실패")
                    _nickok.value = false
                }
        }
    }

    fun independentCheck(indendentok: Boolean) {
        viewModelScope.launch {
            _independentCareerok.value = indendentok
        }
    }

    fun interestCheck(interestok: Boolean) {
        viewModelScope.launch {
            _interestok.value = interestok
        }
    }

    private var _nickname = MutableStateFlow<String>("")
    val nickname: StateFlow<String>
        get() = _nickname
    private var _categories = MutableStateFlow<ArrayList<String>>(ArrayList<String>())
    val categories: StateFlow<ArrayList<String>>
    get() = _categories
    private var _profileImage=MutableStateFlow<String>("")
    val profileImage: StateFlow<String>
        get() = _profileImage
    private var _independenctYear=MutableStateFlow<Int>(0)
    val independenctYear: StateFlow<Int>
        get() = _independenctYear
    private var _independenctMonth=MutableStateFlow<Int>(0)
    val independenctMonth: StateFlow<Int>
        get() = _independenctMonth

    fun saveUserInfoAt4(
        unick: String,
        ucategories: ArrayList<String>,
        uprofileImage: String,
        uindependentYear: Int,
        uindependentMonth: Int
    ) {
        _nickname.value = unick
        _categories.value = ucategories
        _profileImage.value = uprofileImage
        _independenctYear.value = uindependentYear
        _independenctMonth.value = uindependentMonth
        TtoklipApplication.prefs.setString("nickname", unick)
    }

    private var street: String = ""
    fun saveUserStreet(ustreet: String) {
        street = ustreet
    }

    fun savePrivacy() {
        viewModelScope.launch {
            signupRepository.savePrivacy(
                SignupRequest(
                    street,
                    nickname.value,
                    categories.value,
                    profileImage.value,
                    independenctYear.value,
                    independenctMonth.value
                )
            ).onSuccess {
                Log.i("USER-SAVE", "성공")
            }.onFail {
                Log.d("USER-SAVE", "실패")
            }
        }
    }
}