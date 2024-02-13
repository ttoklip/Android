package com.umc.ttoklip.presentation.honeytip.read

import androidx.lifecycle.ViewModel
import com.umc.ttoklip.data.model.honeytip.HoneyTipResponse
import com.umc.ttoklip.data.repository.honeytip.HoneyTipRepository
import com.umc.ttoklip.data.repository.honeytip.HoneyTipRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ReadHoneyTipViewModel @Inject constructor(
    private val repository: HoneyTipRepositoryImpl
): ViewModel(){
    private val _honeyTip = MutableSharedFlow<HoneyTipResponse>()
}