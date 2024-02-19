package com.umc.ttoklip.presentation.hometown

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.model.town.Communities
import com.umc.ttoklip.data.repository.town.MainCommsRepository
import com.umc.ttoklip.module.onError
import com.umc.ttoklip.module.onException
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunicationViewModelImpl @Inject constructor(private val repository: MainCommsRepository) :
    ViewModel(), CommunicationViewModel {
    private val _communities = MutableStateFlow<List<Communities>>(emptyList())
    override val communities: StateFlow<List<Communities>>
        get() = _communities

    val isEnd = MutableStateFlow<Boolean>(false)

    val age = MutableStateFlow<Int>(1)

    override fun getCommunities() {
        viewModelScope.launch {
            repository.getComms().onSuccess {
                _communities.value = it.communities
            }.onError {

            }
        }
    }
}