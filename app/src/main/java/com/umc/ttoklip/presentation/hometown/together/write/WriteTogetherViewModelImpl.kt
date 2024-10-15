package com.umc.ttoklip.presentation.hometown.together.write

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naver.maps.geometry.LatLng
import com.umc.ttoklip.R
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.data.model.town.CreateTogethersRequest
import com.umc.ttoklip.data.repository.naver.NaverRepository
import com.umc.ttoklip.data.repository.town.WriteTogetherRepository
import com.umc.ttoklip.module.onError
import com.umc.ttoklip.module.onException
import com.umc.ttoklip.module.onFail
import com.umc.ttoklip.module.onSuccess
import com.umc.ttoklip.presentation.honeytip.adapter.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class WriteTogetherViewModelImpl @Inject constructor(
    private val repository: WriteTogetherRepository,
    private val naverRepository: NaverRepository,
    @ApplicationContext private val context: Context
) :
    ViewModel(), WriteTogetherViewModel {
    val _title: MutableStateFlow<String> = MutableStateFlow<String>("")
    override val title: StateFlow<String>
        get() = _title

    private val _totalPrice: MutableStateFlow<Long> = MutableStateFlow<Long>(0)
    override val totalPrice: StateFlow<Long>
        get() = _totalPrice

    private val _totalMember: MutableStateFlow<Long> = MutableStateFlow<Long>(0)
    override val totalMember: StateFlow<Long>
        get() = _totalMember

    val _dealPlace: MutableStateFlow<String> = MutableStateFlow<String>("")
    override val dealPlace: StateFlow<String>
        get() = _dealPlace

    val _openLink: MutableStateFlow<String> = MutableStateFlow<String>("")
    override val openLink: StateFlow<String>
        get() = _openLink

    val _content: MutableStateFlow<String> = MutableStateFlow<String>("")
    override val content: StateFlow<String>
        get() = _content

    val _extraUrl: MutableStateFlow<String> = MutableStateFlow<String>("")
    override val extraUrl: StateFlow<String>
        get() = _extraUrl

    val _doneButtonActivated: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    override val doneButtonActivated: StateFlow<Boolean>
        get() = _doneButtonActivated

    val _doneWriteTogether: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    override val doneWriteTogether: StateFlow<Boolean>
        get() = _doneWriteTogether

    private val _closePage: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val closePage: StateFlow<Boolean>
        get() = _closePage

    private val _images: MutableStateFlow<List<Image>> = MutableStateFlow(mutableListOf())
    override val images: StateFlow<List<Image>>
        get() = _images

    private val _postId = MutableStateFlow<Long>(0L)
    override val postId: StateFlow<Long>
        get() = _postId

    private val _address = MutableStateFlow("")
    override val address: StateFlow<String>
        get() = _address

    private val _addressDetail = MutableStateFlow("")
    override val addressDetail: StateFlow<String>
        get() = _addressDetail

    private val _isInputComplete = MutableStateFlow(false)
    override val isInputComplete: StateFlow<Boolean>
        get() = _isInputComplete

    private val _tradeLocationEvent = MutableSharedFlow<WriteTogetherViewModel.TradeLocationEvent>()
    override val tradeLocationEvent: SharedFlow<WriteTogetherViewModel.TradeLocationEvent>
        get() = _tradeLocationEvent

    private val _isEdit = MutableStateFlow(false)
    override val isEdit: StateFlow<Boolean>
        get() = _isEdit

    private val _isEditDone = MutableSharedFlow<Boolean>()
    override val isEditDone: MutableSharedFlow<Boolean>
        get() = _isEditDone

    private val _includeSwear = MutableSharedFlow<String>()
    override val includeSwear: SharedFlow<String>
        get() = _includeSwear


    override fun setIsEdit(isEdit: Boolean) {
        _isEdit.value = isEdit
    }

    override fun setTitle(title: String) {
        _title.value = title
    }

    override fun setContent(content: String) {
        _content.value = content
    }

    override fun setImage(image: List<Image>) {
        _images.value = image
    }

    override fun setOpenLink(openLink: String) {
        _openLink.value = openLink
    }

    override fun setTotalPrice(totalPrice: Long) {
        _totalPrice.value = totalPrice
        Log.d("set price", _totalPrice.value.toString())
    }

    override fun setTotalMember(totalMember: Long) {
        _totalMember.value = totalMember
    }

    override fun setAddress(address: String) {
        if (address.isEmpty()) {
            return
        }
        _address.value = address
        _dealPlace.value = address
        Log.d("setAddress", _dealPlace.value.toString())
    }

    override fun setPostId(postId: Long) {
        _postId.value = postId
    }

    override fun setDoneButtonActivated(doneButtonActivated: Boolean) {
        _doneButtonActivated.value = doneButtonActivated
    }

    override fun setAddressDetail(addressDetail: String) {
        if (addressDetail.isEmpty()) {
            return
        }
        _addressDetail.value = addressDetail
        _dealPlace.value += "($addressDetail)"
        Log.d("setAddressDetail", addressDetail)
    }

    override fun setIsInputComplete() {
        _isInputComplete.value = _isInputComplete.value.not()
        eventTradeLocation(
            WriteTogetherViewModel.TradeLocationEvent.InputAddressComplete(
                _isInputComplete.value
            )
        )
    }


    override fun addImages(images: List<Image>) {
        val combined = _images.value + images
        _images.value = combined
    }


    override fun checkDone() {
        _doneButtonActivated.value =
            title.value.isNotBlank() && openLink.value.isNotBlank() && content.value.isNotBlank() && totalPrice.value > 0 && totalMember.value > 0
        Log.d("checkDone", _doneButtonActivated.value.toString())
    }

    override fun doneButtonClick() {
        _doneWriteTogether.value = true
    }

    override fun writeTogether(images: List<MultipartBody.Part?>) {
        val request = CreateTogethersRequest(
            title = title.value,
            content = content.value,
            totalPrice = totalPrice.value,
            location = dealPlace.value,
            chatUrl = openLink.value,
            party = totalMember.value,
            itemUrls = listOf(extraUrl.value),
            images = images
        )
        Log.d("request", request.toString())
        viewModelScope.launch {
            repository.createTogether(request).onSuccess {
                _postId.value = it.message.replace(("[^\\d]").toRegex(), "").toLong()
                _closePage.value = true
                delay(1000)
            }.onError {
                Log.d("writetogethererror", it.toString())
            }.onFail { message ->
//                _includeSwear.emit(message)
                _includeSwear.emit(TtoklipApplication.getString(R.string.post_fail))
            }
        }
    }

    override fun fetchGeocoding(query: String) {
        viewModelScope.launch {
            naverRepository.fetchGeocoding(query).onSuccess { response ->
                val result = response.addresses.firstOrNull()
                if (result == null) {
                    eventTradeLocation(WriteTogetherViewModel.TradeLocationEvent.ToastException("주소를 정확히 입력해주세요."))
                    return@launch
                } else {
                    with(result) {
                        val latLng = LatLng(y.toDouble(), x.toDouble())
                        eventTradeLocation(
                            WriteTogetherViewModel.TradeLocationEvent.CheckLocation(
                                latLng
                            )
                        )
                        setAddress(result.jibunAddress)
                    }
                }
                Log.d("naver", response.toString())
            }.onException {
                Log.d("error", it.toString())
            }
        }
    }
    override fun eventTradeLocation(event: WriteTogetherViewModel.TradeLocationEvent) {
        viewModelScope.launch {
            _tradeLocationEvent.emit(event)
        }
    }

    override fun patchTogether(images: List<MultipartBody.Part?>) {
        viewModelScope.launch {
            repository.patchTogether(
                title = convertStringToTextPlain(title.value),
                content = convertStringToTextPlain(content.value),
                totalPrice = convertStringToTextPlain(totalPrice.value.toString()),
                location = convertStringToTextPlain(address.value),
                chatUrl = convertStringToTextPlain(openLink.value),
                party = convertStringToTextPlain(totalMember.value.toString()),
                images = images,
                itemUrls = convertStringToTextPlain(extraUrl.value),
                postId = postId.value
            ).onSuccess {
                _isEditDone.emit(true)
                Log.d("patch Together", it.toString())
            }.onFail {message ->
                _includeSwear.emit(message)
            }
        }
    }

    override fun fetchReverseGeocoding(latLng: LatLng) {
        viewModelScope.launch {
            naverRepository.fetchReverseGeocodingInfo("${latLng.longitude},${latLng.latitude}", "json").onSuccess {
                val address = it.results.first()
                with(address.region){
                    /*setAddress(area1.name +
                            (if(area2.name.isNotEmpty()) " " + area2.name else "") +
                            (if(area3.name.isNotEmpty()) " " + area3.name else "") +
                            (if(area4.name.isNotEmpty()) " " + area4.name else ""))*/

                    setAddress(area1.name +
                            (if(area2.name.isNullOrEmpty()) "" else " " + area2.name) +
                            (if(area3.name.isNullOrEmpty()) "" else " " + area3.name) +
                            (if(area4.name.isNullOrEmpty()) "" else " " + area4.name ))
                }
            }
        }
    }

    private fun convertStringToTextPlain(string: String): RequestBody {
        return string.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    private fun createRequestBodyFromList(list: List<Int>): RequestBody {
        val listString = list.joinToString(",") // List를 쉼표로 구분된 문자열로 변환
        return RequestBody.create("text/plain".toMediaTypeOrNull(), listString)
    }
}