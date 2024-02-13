package com.umc.ttoklip.presentation.login

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.data.model.login.LoginRequest
import com.umc.ttoklip.data.repository.login.LoginRepositoryImpl
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepositoryImpl
) : ViewModel() {

    private val _isFirstLogin = MutableStateFlow<Boolean>(true)
    val isFirstLogin: StateFlow<Boolean>
        get() = _isFirstLogin
    private val _isLogin=MutableStateFlow<Boolean>(false)
    val isLogin:StateFlow<Boolean>
        get() = _isLogin

    fun postLogin(request: LoginRequest) {
        viewModelScope.launch {
            loginRepository.postLogin(request)
                .onSuccess {
                    TtoklipApplication.prefs.setString("jwt",it.jwtToken)
                    _isFirstLogin.value=it.ifFirstLogin
                    _isLogin.value=true
                }.onFail {
                    Log.d("LOGIN-API", "login 실패")
                }
        }
    }

    fun initIsLogin(){
        _isLogin.value=false
    }
}