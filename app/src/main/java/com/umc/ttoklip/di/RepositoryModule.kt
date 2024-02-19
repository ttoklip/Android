package com.umc.ttoklip.di

import com.umc.ttoklip.data.api.LoginApi
import com.umc.ttoklip.data.api.MyPageApi
import com.umc.ttoklip.data.api.NewsApi
import com.umc.ttoklip.data.api.Search2Api
import com.umc.ttoklip.data.api.SearchApi
import com.umc.ttoklip.data.repository.news.NewsRepository
import com.umc.ttoklip.data.repository.news.NewsRepositoryImpl
import com.umc.ttoklip.data.repository.scrap.ScrapRepository
import com.umc.ttoklip.data.repository.scrap.ScrapRepositoryImpl
import com.umc.ttoklip.data.repository.search.Search2Repository
import com.umc.ttoklip.data.repository.search.Search2RepositoryImpl
import com.umc.ttoklip.data.repository.search.SearchRepository
import com.umc.ttoklip.data.repository.search.SearchRepositoryImpl
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

    @Provides
    @Singleton
    fun providesSearchRepository(api: SearchApi): SearchRepository =
        SearchRepositoryImpl(api)

    @Provides
    @Singleton
    fun providesSearch2Repository(api: Search2Api): Search2Repository =
        Search2RepositoryImpl(api)


    @Provides
    @Singleton
    fun providesScrapRepository(api: MyPageApi): ScrapRepository =
        ScrapRepositoryImpl(api)

}