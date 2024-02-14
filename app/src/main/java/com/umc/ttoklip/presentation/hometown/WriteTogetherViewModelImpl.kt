package com.umc.ttoklip.presentation.hometown

import androidx.lifecycle.ViewModel
import com.umc.ttoklip.data.repository.town.WriteTogetherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WriteTogetherViewModelImpl @Inject constructor(val repository: WriteTogetherRepository) :
    ViewModel(), WriteTogetherViewModel {
}