package com.umc.ttoklip.data.repository.stranger

import com.umc.ttoklip.data.model.signup.SignupResponse
import com.umc.ttoklip.data.model.stranger.StrangerResponse
import com.umc.ttoklip.module.NetworkResult

interface StrangerRepository {
    suspend fun getStranger(nick:String): NetworkResult<StrangerResponse>
}