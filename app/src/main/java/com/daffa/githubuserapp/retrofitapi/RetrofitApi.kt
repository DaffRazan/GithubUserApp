package com.daffa.githubuserapp.retrofitapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitApi {
    private const val BASE_URL = "https://api.github.com/"

    private val retrofitClient = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(Client.okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())

    val API_CLIENT: ApiEndpointInterface = retrofitClient.build().create(ApiEndpointInterface::class.java)
}