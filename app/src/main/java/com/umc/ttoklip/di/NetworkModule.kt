package com.umc.ttoklip.di

import com.umc.ttoklip.R
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.data.api.HoneyTipApi
import com.umc.ttoklip.data.api.NewsApi
import com.umc.ttoklip.data.api.TestApi
import com.umc.ttoklip.data.repository.news.NewsRepository
import com.umc.ttoklip.data.repository.news.NewsRepositoryImpl
import com.umc.ttoklip.module.HttpRequestInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    const val NETWORK_EXCEPTION_OFFLINE_CASE = "network status is offline"
    const val NETWORK_EXCEPTION_BODY_IS_NULL = "result body is null"

    @Provides
    @Singleton
    fun provideOKHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }


        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .retryOnConnectionFailure(false)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(TtoklipApplication.getString(R.string.base_url))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideTestApi(retrofit: Retrofit): TestApi {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideHoneyTipApi(retrofit: Retrofit): HoneyTipApi {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): NewsApi {
        return retrofit.buildService()
    }

    private inline fun <reified T> Retrofit.buildService(): T {
        return this.create(T::class.java)
    }
}