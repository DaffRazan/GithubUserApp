package com.daffa.githubuserapp.retrofitapi.model

data class UserSearchModel(
    val incomplete_results: Boolean,
    val items: List<User>,
    val total_count: Int
)