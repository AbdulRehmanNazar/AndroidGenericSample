package com.ar.sample.di

import android.content.Context
import com.ar.sample.security.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


/**
 * @Author: Abdul Rehman
 */

@Module
@InstallIn(SingletonComponent::class)
object BasicModule {


    @Provides
    fun provideTokenManager(@ApplicationContext appContext: Context): TokenManager {
        val tokenManager = TokenManager(appContext)
        return tokenManager
    }

}