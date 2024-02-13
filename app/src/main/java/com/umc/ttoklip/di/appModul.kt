package com.umc.ttoklip.di

import com.umc.ttoklip.R
import com.umc.ttoklip.data.api.KakaoApi
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object appModul {
}