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
    class kakaoAddress {
        companion object {
            var BASE_URL = R.string.kakao.toString()
            var API_KEY = R.string.kakao_api_key.toString()
        }

        object kakaoApiRetrofitClient {
            private val retrofit: Retrofit.Builder by lazy {
                Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
            }
            val apiService: KakaoApi by lazy {
                retrofit.build().create(KakaoApi::class.java)
            }
        }
    }
}