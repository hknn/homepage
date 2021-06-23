package com.example.homepage.api.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor constructor(private val addHeader: (Request.Builder) -> Unit) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
        addHeader.invoke(newRequest)
        return chain.proceed(newRequest.build())
    }
}