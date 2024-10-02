package com.umc.ttoklip.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.data.model.fcm.FCMTokenRequest
import com.umc.ttoklip.data.model.home.HomeResponse
import com.umc.ttoklip.data.repository.fcm.FCMRepository
import com.umc.ttoklip.data.repository.home.HomeRepository
import com.umc.ttoklip.data.repository.naver.NaverRepository
import com.umc.ttoklip.module.NetworkResult
import com.umc.ttoklip.module.onException
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    private val homeRepository: HomeRepository,
    private val fcmRepository: FCMRepository
) : ViewModel(), HomeViewModel {

    private val _haveWork: MutableStateFlow<Boolean> = MutableStateFlow(true)
    override val haveWork: StateFlow<Boolean>
        get() = _haveWork

    private val _doneWork: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val doneWork: StateFlow<Boolean>
        get() = _doneWork

    private val _activityBus = MutableSharedFlow<HomeViewModel.ActivityEventBus>()
    override val activityBus: SharedFlow<HomeViewModel.ActivityEventBus>
        get() = _activityBus.asSharedFlow()

    private val _mainData = MutableStateFlow(HomeResponse())
    override val mainData: StateFlow<HomeResponse>
        get() = _mainData

    private val _isTownTarget = MutableStateFlow(false)
    override val isTownTarget: StateFlow<Boolean>
        get() = _isTownTarget

    init {
        getUserStreet()
    }

    override fun clickDelayWork() {
        viewModelScope.launch {
            _haveWork.emit(haveWork.value.not())
        }
    }

    override fun clickDoneWork() {
        viewModelScope.launch {
            _doneWork.emit(doneWork.value.not())
        }
    }

    override fun clickMoreNews() {
        viewModelScope.launch {
            _activityBus.emit(HomeViewModel.ActivityEventBus.NEWS_DETAIL)
        }
    }

    override fun clickMoreTip() {
        viewModelScope.launch {
            _activityBus.emit(HomeViewModel.ActivityEventBus.TIP_DETAIL)
        }
    }

    override fun clickMoreGroupBuy() {
        viewModelScope.launch {
            _activityBus.emit(HomeViewModel.ActivityEventBus.GROUP_BUY_DETAIL)
        }
    }

    override fun clickAlarm() {
        viewModelScope.launch {
            _activityBus.emit(HomeViewModel.ActivityEventBus.ALARM)
        }
    }

    override fun clickSearch() {
        viewModelScope.launch {
            _activityBus.emit(HomeViewModel.ActivityEventBus.SEARCH)
        }
    }

    override fun getMain(expirationToken:()->Unit) {
        viewModelScope.launch {
            try {
                homeRepository.getHomeMain()
                    .onSuccess {
                        _mainData.emit(it)
                        TtoklipApplication.prefs.setString("nickname", it.currentMemberNickname)
                    }.onFail {
                        expirationToken()
                    }.onException {
                        throw it
                    }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("예외", "$e")
            }
        }
    }

    override fun patchFCM(token: String) {
        viewModelScope.launch {
            try {
                fcmRepository.patchFCMToken(FCMTokenRequest(token))
                    .onSuccess {

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

    private fun getUserStreet() {
        try {
            viewModelScope.launch {
                homeRepository.getUserStreet().onSuccess {
                    _isTownTarget.value = it.writerLiveInSeoul
                }.onFail {

                }.onException {
                    throw it
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("예외", "$e")
        }
    }

}