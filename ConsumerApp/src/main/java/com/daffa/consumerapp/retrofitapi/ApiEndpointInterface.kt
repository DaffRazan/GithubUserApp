package com.daffa.consumerapp.retrofitapi

import com.daffa.consumerapp.database.UserEntity
import com.daffa.consumerapp.retrofitapi.model.User
import com.daffa.consumerapp.retrofitapi.model.UserSearchModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiEndpointInterface {
    @GET("search/users")
    fun userSearch(@Query("q") searchUser: String): Call<UserSearchModel>

    @GET("users/{username}")
    fun userDetail(@Path("username") searchUser: String): Call<UserEntity>

    @GET("users/{username}/followers")
    fun userFollowers(@Path("username") searchUser: String): Call<List<User>>

    @GET("users/{username}/following")
    fun userFollowing(@Path("username") searchUser: String): Call<List<User>>
}