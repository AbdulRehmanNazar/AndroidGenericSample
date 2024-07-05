package com.ar.sample.security

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder().addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer ${tokenManager.getAccessToken()}")

        val request = requestBuilder.build()
        val response = chain.proceed(request)
        //Handle unauthorized case by using some event bus like to logout all the places
        if (response.code == 401) {

        }
        return response
    }
}