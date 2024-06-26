package com.umc.ttoklip.presentation.hometown.together.write.tradelocation

import androidx.lifecycle.ViewModel
import com.umc.ttoklip.data.repository.naver.NaverRepository
import com.umc.ttoklip.data.repository.naver.NaverRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TradeLocationViewModel @Inject constructor(
    private val repository: NaverRepository
) : ViewModel() {

}