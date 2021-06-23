package com.daffa.consumerapp.retrofitapi

import okhttp3.Interceptor
import okhttp3.OkHttpClient

object Client {
    //REPLACE WITH YOUR OWN GITHUB PERSONAL ACCESS TOKEN
    private const val TOKEN = "token yourtoken"

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