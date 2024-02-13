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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupRepository: SignupRepositoryImpl
) : ViewModel() {

    fun nickCheck(nick:String) {
        viewModelScope.launch {
            signupRepository.checkNickname(nick)
                .onSuccess {
                    Log.i("nick check","성공")
                }.onFail {
                    Log.d("nick check", "실패")
                }
        }
    }
}