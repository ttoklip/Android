package com.umc.ttoklip.presentation.hometown

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.model.town.TownMainResponse
import com.umc.ttoklip.data.repository.town.TownMainRepository
import com.umc.ttoklip.module.onException
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import com.umc.ttoklip.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyHometownViewModelImpl @Inject constructor(
    private val repository: TownMainRepository
) : ViewModel(), MyHometownViewModel {

    private val _mainData = MutableStateFlow<UiState<TownMainResponse>>(UiState.Empty)
    override val mainData: StateFlow<UiState<TownMainResponse>>
        get() = _mainData

    private val _errorData = MutableStateFlow("")
    override val errorData: StateFlow<String>
        get() = _errorData

    override fun getM() {
        viewModelScope.launch {
            try {
                _mainData.value = UiState.Loading
                repository.getTerm()
                    .onSuccess {
                        _mainData.emit(UiState.Success(it))
                        _errorData.value = ""
                    }.onFail {
                        _errorData.value = it
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