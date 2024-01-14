package com.umc.ttoklip.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.model.TestResponse
import com.umc.ttoklip.data.repository.TestRepositoryImpl
import com.umc.ttoklip.module.handleApi
import com.umc.ttoklip.module.onError
import com.umc.ttoklip.module.onException
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


//테스트 viewmodel
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TestRepositoryImpl
) : ViewModel() {
    private val _testResult = MutableLiveData<TestResponse>()
    val searchResult: LiveData<TestResponse> get() = _testResult

    private val _testError = MutableLiveData<String>()
    val testError: LiveData<String> get() = _testError

    fun test() = viewModelScope.launch(Dispatchers.IO) {
        val result = handleApi({ repository.test() }, { TestResponse -> TestResponse })
        withContext(Dispatchers.Main) {
            result.onSuccess { _testResult.value = it }
            result.onFail { _testError.value = "api 연동 실패" }
            result.onError { _testError.value = "api 연동 error: $it" }
            result.onException { _testError.value = "api 연동 exception: $it" }
        }
    }
}