package com.umc.ttoklip.presentation.hometown

import androidx.lifecycle.ViewModel
import com.umc.ttoklip.data.repository.town.WriteCommsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WriteCommunicationViewModelImpl @Inject constructor(private val repository: WriteCommsRepository) :
    ViewModel(), WriteCommunicationViewModel {
}