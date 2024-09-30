package com.umc.ttoklip.presentation.mypage.manageinfo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naver.maps.geometry.LatLng
import com.umc.ttoklip.data.model.mypage.MyPageInfoResponse
import com.umc.ttoklip.data.repository.mypage.MyPageRepository2Impl
import com.umc.ttoklip.data.repository.naver.NaverRepository
import com.umc.ttoklip.module.onError
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class ManageMyInfoViewModel @Inject constructor(
    private val repository: MyPageRepository2Impl,
    private val naverRepository: NaverRepository
) : ViewModel() {
    private val _myPageInfo = MutableStateFlow<MyPageInfoResponse>(MyPageInfoResponse())
    val myPageInfo = _myPageInfo.asStateFlow()

    private val _myPageEvent = MutableSharedFlow<Event>()
    val myPageEvent = _myPageEvent.asSharedFlow()

    val independentDuration = MutableStateFlow("")

    private val _nickok = MutableSharedFlow<Boolean>()
    val nickok = _nickok.asSharedFlow()

    val address = MutableStateFlow("")

    val toast = MutableSharedFlow<String>()

    private val _isButtonEnabled = MutableStateFlow<Boolean>(true)
    val isButtonEnabled: StateFlow<Boolean> get() = _isButtonEnabled

    private var isRequireNickCheck = false

    private var editedNickname = ""
    private var editedCategory = emptyList<String>()
    private var editedAddress = ""
    private var editedIndependentYear = -1
    private var editedIndependentMonth = -1

    private val _isAddressEdit = MutableStateFlow(false)
    val isAddressEdit = _isAddressEdit.asStateFlow()

    sealed class Event {
        object EditMyPageInfo : Event()
    }

    fun setIsAddressEdit(isEdit: Boolean){
        this._isAddressEdit.value = isEdit
        _isButtonEnabled.value = true
        Log.d("isbutton", isButtonEnabled.value.toString())
    }

    fun setAddress(address: String, isInputDirect: Boolean){
        if(isInputDirect){
            _myPageInfo.value = _myPageInfo.value.copy().apply { street = address }
        } else {
            this.address.value = address
        }
    }

    fun editNickname(nickname: String) {
        if (isAddressEdit.value){
            return
        }
        isRequireNickCheck = nickname != _myPageInfo.value.nickname
        _isButtonEnabled.value = isRequireNickCheck
        Log.d("editNickname", _isButtonEnabled.value.toString())
        editedNickname = nickname
        Log.d("isRequireNickCheck", isRequireNickCheck.toString())
    }

    fun editCategory(category: List<String>) {
        if (isAddressEdit.value){
            return
        }
        editedCategory = category
        if (!isRequireNickCheck) {
            _isButtonEnabled.value = category != _myPageInfo.value.interests.map { it.categoryName }
        }
    }

    fun editIndependentCareer(independentYear: Int, independentMonth: Int) {
        if (isAddressEdit.value){
            return
        }
        editedIndependentYear = independentYear
        editedIndependentMonth = independentMonth
        if (!isRequireNickCheck) {
            _isButtonEnabled.value =
                (_myPageInfo.value.independentYear != independentYear) or (_myPageInfo.value.independentMonth != independentMonth)
        }
    }

    private fun setIsButtonEnabled() {
        if (editedNickname != _myPageInfo.value.nickname) {
            _isButtonEnabled.value = true
        }

        if (editedCategory != _myPageInfo.value.interests.map { it.categoryName }) {
            _isButtonEnabled.value = true
        }

        if (editedAddress != _myPageInfo.value.street) {
            _isButtonEnabled.value = true
        }

        if ((editedIndependentYear != _myPageInfo.value.independentYear) or (editedIndependentMonth != _myPageInfo.value.independentMonth)) {
            _isButtonEnabled.value = true
        }
    }

    private fun convertStringToTextPlain(string: String): RequestBody {
        return string.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    private fun createRequestBodyFromList(list: List<String>): RequestBody {
        val listString = list.joinToString(",") // List를 쉼표로 구분된 문자열로 변환
        return RequestBody.create("text/plain".toMediaTypeOrNull(), listString)
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _myPageEvent.emit(event)
        }
    }

    fun getMyPageInfo() {
        if(isAddressEdit.value) {
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMyPageInfo().onSuccess {
                _myPageInfo.emit(it)
                independentDuration.emit("${it.independentYear}년 ${it.independentMonth}개월")
            }
        }
    }

    fun editMyPageInfo(
        street: String,
        nickname: String,
        categories: List<String>,
        profileImage: MultipartBody.Part?,
        independentYear: Int,
        independentMonth: Int
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            if(isRequireNickCheck){
                toast.emit("먼저 닉네임 중복확인을 해주세요.")
                return@launch
            }
            createRequestBodyFromList(categories)
            repository.editMyPageInfo(
                convertStringToTextPlain(street),
                convertStringToTextPlain(nickname),
                createRequestBodyFromList(categories),
                profileImage,
                convertStringToTextPlain(independentYear.toString()),
                convertStringToTextPlain(independentMonth.toString())
            ).onSuccess {
                Log.d("edit info", it.toString())
                event(Event.EditMyPageInfo)
            }.onError {
                Log.d("error", it.toString())
            }
        }
    }

    fun nickCheck(nick: String) {
        viewModelScope.launch {
            repository.checkNickname(nick)
                .onSuccess {
                    Log.i("nick check", "성공")
                    _nickok.emit(true)
                    isRequireNickCheck = false
                    _isButtonEnabled.value = true
                    setIsButtonEnabled()
                }.onFail {
                    Log.d("nick check", "실패")
                    _nickok.emit(false)
                    isRequireNickCheck = true
                }
        }
    }

    fun fetchReverseGeocoding(coords: LatLng, output: String) {
        viewModelScope.launch {
            naverRepository.fetchReverseGeocodingInfo(
                "${coords.longitude}, ${coords.latitude}",
                output
            ).onSuccess {
                Log.d("gc", it.toString())
                if(it.results.isNotEmpty()) {
                    val location = it.results.first()
                    address.value = if (location.land.number2.isNullOrEmpty()) {
                        with(location) {
                            region.area1.name + " " + region.area2.name +
                                    " " + region.area3.name + " " + region.area4.name +
                                    " " + land.number1
                        }
                    } else {
                        with(location) {
                            region.area1.name + " " + region.area2.name +
                                    " " + region.area3.name + " " + region.area4.name +
                                    " " + land.number1 + "-" + land.number2
                        }
                    }
                }
                _myPageInfo.value = _myPageInfo.value.copy().apply { street = address.value }
                Log.d("address value", address.value)
            }
        }
    }
}