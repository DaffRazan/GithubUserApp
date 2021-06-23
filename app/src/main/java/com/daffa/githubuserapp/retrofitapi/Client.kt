package com.daffa.githubuserapp.retrofitapi

import okhttp3.Interceptor
import okhttp3.OkHttpClient

object Client {
    private const val TOKEN = "token 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"

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