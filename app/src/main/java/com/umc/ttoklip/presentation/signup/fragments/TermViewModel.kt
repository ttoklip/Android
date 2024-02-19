package com.umc.ttoklip.presentation.signup.fragments

import androidx.lifecycle.ViewModel
import com.umc.ttoklip.data.repository.signup.SignupRepositoryImpl
import javax.inject.Inject

class TermViewModel @Inject constructor(
    private val signupRepository: SignupRepositoryImpl
) : ViewModel() {
}