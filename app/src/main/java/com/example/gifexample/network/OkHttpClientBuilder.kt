package com.example.gifexample.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Class that gives the object for OkHttpClient and also adds necessary interceptors and API key
 */
class OkHttpClientBuilder : OkHttpClient() {
    companion object {
        fun get(): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val builder = Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor {
                    val request = it.request().newBuilder()
                    val originalHttpUrl = it.request().url
                    val url = originalHttpUrl.newBuilder().addQueryParameter("api_key", "QqSVO2SCUmQwW2RJPCQTbBQ2QCgvxYpO").build()
                    request.url(url)
                    return@addInterceptor it.proceed(request.build())
                }
                .build()
            return builder
        }
    }
}