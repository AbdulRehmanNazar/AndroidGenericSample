package com.ar.sample.di

import com.ar.sample.BuildConfig
import com.ar.sample.data.remote.api.APIService
import com.ar.sample.security.AuthInterceptor
import com.ar.sample.security.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton


/**
 * @Author: Abdul Rehman
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun getBaseURL(): String = BuildConfig.API_BASE_URL

    @Provides
    fun provideOkhttpClient(tokenManager: TokenManager): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
//            .addInterceptor(AuthInterceptor(tokenManager))
            .addInterceptor(interceptor).build();
    }


    @Singleton
    @Provides
    fun provideRetrofit(
        baseURL: String, client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder().baseUrl(baseURL).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun provideAPIService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }
}