package com.umc.ttoklip.presentation.hometown

import androidx.lifecycle.ViewModel
import com.umc.ttoklip.data.repository.town.ReadCommsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommunicationViewModelImpl @Inject constructor(private val repository: ReadCommsRepository) :
    ViewModel(), CommunicationViewModel {
}