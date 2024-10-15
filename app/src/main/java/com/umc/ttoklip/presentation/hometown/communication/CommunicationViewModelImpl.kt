package com.umc.ttoklip.presentation.hometown.communication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.model.town.Communities
import com.umc.ttoklip.data.repository.town.MainCommsRepository
import com.umc.ttoklip.data.repository.town.MainTogethersRepository
import com.umc.ttoklip.module.onError
import com.umc.ttoklip.module.onException
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class CommunicationViewModelImpl @Inject constructor(
    private val repository: MainCommsRepository,
    private val mainTogethersRepository: MainTogethersRepository
) :
    ViewModel(), CommunicationViewModel {
    private val _communities = MutableStateFlow<List<Communities>>(emptyList())
    override val communities: StateFlow<List<Communities>>
        get() = _communities

    private val _criteria = MutableStateFlow("CITY")
    override val criteria: StateFlow<String>
        get() = _criteria

    private val _streetInfo = MutableStateFlow<List<String>>(emptyList())
    override val streetInfo: StateFlow<List<String>>
        get() = _streetInfo

    override fun setCriteria(position: Int) {
        _criteria.value = when (position) {
            0 -> "CITY"
            1 -> "DISTRICT"
            2 -> "TOWN"
            else -> throw IllegalArgumentException()
        }
        if (_criteria.value.isNotEmpty()) {
            _communities.value = listOf()
            page.value = 0
            isEnd.value = false
            getCommunities()
        }
    }


    private val isEnd = MutableStateFlow<Boolean>(false)

    private val page = MutableStateFlow<Int>(0)

    override fun getMemberStreetInfo() {
        viewModelScope.launch {
            mainTogethersRepository.getMemberStreetInfo().onSuccess {
                _streetInfo.value = it.street.split(" ")
                Log.d("street INfo", it.street)
            }
        }
    }

    override fun getCommunities() {
        if (!isEnd.value) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    repository.getComms(page = page.value, criteria.value)
                        .onSuccess {
                            _communities.value = communities.value + it.communities
                            page.value += 1
                            isEnd.value = it.isLast
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
}