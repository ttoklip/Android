package com.umc.ttoklip.di

import android.content.Context
import androidx.room.Room
import com.umc.ttoklip.data.db.AppDatabase
import com.umc.ttoklip.data.db.HistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DBModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room
        .databaseBuilder(context, AppDatabase::class.java, "Ttoklip.db")
        .build()

    @Singleton
    @Provides
    fun provideHistoryDao(appDatabase: AppDatabase): HistoryDao = appDatabase.historyDao()
}