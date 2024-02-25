package com.umc.ttoklip.presentation.hometown

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.model.town.townMainResponse
import com.umc.ttoklip.data.repository.town.TownMainRepository
import com.umc.ttoklip.module.onException
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyHometownViewModelImpl @Inject constructor(
    private val repository: TownMainRepository
) : ViewModel(), MyHometownViewModel {

    private val _mainData = MutableStateFlow(townMainResponse())
    override val mainData: StateFlow<townMainResponse>
        get() = _mainData

    override fun getM() {
        viewModelScope.launch {
            try {
                repository.getTerm()
                    .onSuccess {
                        _mainData.emit(it)
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