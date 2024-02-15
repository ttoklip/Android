package com.umc.ttoklip.presentation.hometown

import androidx.lifecycle.ViewModel
import com.umc.ttoklip.data.repository.town.WriteTogetherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class WriteTogetherViewModelImpl @Inject constructor(val repository: WriteTogetherRepository) :
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

    override fun setTotalPrice(totalPrice: Long) {
        _totalPrice.value = totalPrice
    }

    override fun setTotalMember(totalMember: Long) {
        _totalMember.value = totalMember
    }


    override fun checkDone() {
        _doneButtonActivated.value =
            title.value.isNotBlank() && openLink.value.isNotBlank() && content.value.isNotBlank() && totalPrice.value > 0 && totalMember.value > 0
    }

    override fun doneButtonClicked() {

        _doneWriteTogether.value = true
    }

}