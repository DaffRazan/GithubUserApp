package com.daffa.consumerapp.retrofitapi

import okhttp3.Interceptor
import okhttp3.OkHttpClient

object Client {
    private const val TOKEN = "token bf3bc363b9321fe1fe669bebc5e3b8973f8b7701"

    val okHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(
            Interceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.addHeader("Authorization", TOKEN)
                builder.addHeader("User-Agent", "request")
                return@Interceptor chain.proceed(builder.build())
            }
        )
    }.build()
}