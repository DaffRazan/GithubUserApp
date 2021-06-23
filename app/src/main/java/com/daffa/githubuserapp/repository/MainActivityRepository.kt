package com.daffa.githubuserapp.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.daffa.githubuserapp.retrofitapi.RetrofitApi
import com.daffa.githubuserapp.retrofitapi.model.User
import com.daffa.githubuserapp.retrofitapi.model.UserSearchModel
import com.daffa.githubuserapp.view.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityRepository(val application: Application) {
    val listUser = MutableLiveData<List<User>>()
    val progressBar = MutableLiveData<Boolean>()
    val mainActivity = MainActivity()

    fun searchUser(query: String) {
        // Show loading when accessing API
        progressBar.value = true

        // Network call
        RetrofitApi.API_CLIENT.userSearch(query).enqueue(object : Callback<UserSearchModel> {
            override fun onResponse(
                call: Call<UserSearchModel>,
                response: Response<UserSearchModel>
            ) {
                listUser.value = response.body()?.items
                Log.d("response", listUser.toString())
                progressBar.value = false
            }

            override fun onFailure(call: Call<UserSearchModel>, t: Throwable) {
                progressBar.value = false
                mainActivity.getToastOnFailure()
            }
        })
    }
}