package com.daffa.githubuserapp.repository

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.daffa.githubuserapp.retrofitapi.RetrofitApi
import com.daffa.githubuserapp.retrofitapi.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersFragmentRepository(val application: Application) {
    val listFollowers = MutableLiveData<List<User>>()
    val progressBar = MutableLiveData<Boolean>()

    fun getFollowers(username: String){
        progressBar.value = true
        val service = RetrofitApi.API_CLIENT

        service.userFollowers(username).enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                listFollowers.value = response.body()
                progressBar.value = false
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                progressBar.value = false
                Toast.makeText(application, t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }
}