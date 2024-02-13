package com.umc.ttoklip.di

import com.umc.ttoklip.data.api.LoginApi
import com.umc.ttoklip.data.api.NewsApi
import com.umc.ttoklip.data.repository.login.LoginRepository
import com.umc.ttoklip.data.repository.login.LoginRepositoryImpl
import com.umc.ttoklip.data.repository.news.NewsRepository
import com.umc.ttoklip.data.repository.news.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    @Singleton
    fun providesNewsRepository(api: NewsApi): NewsRepository =
        NewsRepositoryImpl(api)

}