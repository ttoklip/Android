package com.umc.ttoklip.presentation.signup.location

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.data.model.KakaoResponse
import com.umc.ttoklip.data.repository.location.DirectLocationRepositoryImpl
import com.umc.ttoklip.module.onError
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DirectLocationViewModel @Inject constructor(
    private val directLocationRepository: DirectLocationRepositoryImpl
): ViewModel() {

    private var addressList_= MutableStateFlow<ArrayList<KakaoResponse.Place>>(ArrayList())
    val addressList:StateFlow<ArrayList<KakaoResponse.Place>>
        get() = addressList_

    fun searchAddress(address:String){
        viewModelScope.launch {
            directLocationRepository.getDirectAddress(address)
                .onSuccess {
                    addressList_.emit(it.documents as ArrayList<KakaoResponse.Place>)
                    Log.i("DirectLocation","장소 검색 성공"+addressList.value[0])
                }.onFail {
                    Log.i("DirectLocation","장소 검색 실패")
                }.onError {
                    Log.d("DirectLocation","장소 검색 오류"+it)
                }
        }
    }
}