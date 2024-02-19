package com.umc.ttoklip.di

import android.util.Log
import com.umc.ttoklip.R
import com.umc.ttoklip.data.api.KakaoApi
import com.umc.ttoklip.data.api.TermApi
import com.umc.ttoklip.data.model.KakaoResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KakaoModule {

    private val BASE_URL = R.string.kakao.toString()
    @Provides
    @Singleton
    fun kakaoApiRetrofitClient(keyword: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(KakaoApi::class.java)
    }
}