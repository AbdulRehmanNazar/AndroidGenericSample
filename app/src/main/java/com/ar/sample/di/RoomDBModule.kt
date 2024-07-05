package com.ar.sample.di

import android.content.Context
import com.ar.sample.data.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * @Author: Abdul Rehman
 */

@Module
@InstallIn(SingletonComponent::class)
object RoomDBModule {
    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.getDatabase(appContext)
    }
}