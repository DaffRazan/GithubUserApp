package com.daffa.githubuserapp.repository

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.daffa.githubuserapp.retrofitapi.RetrofitApi
import com.daffa.githubuserapp.retrofitapi.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingFragmentRepository(val application: Application) {
    val listFollowing = MutableLiveData<List<User>>()
    val progressBar = MutableLiveData<Boolean>()

    fun getFollowing(username: String){
        progressBar.value = true
        val service = RetrofitApi.API_CLIENT

        service.userFollowing(username).enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                listFollowing.value = response.body()
                progressBar.value = false
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                progressBar.value = false
                Toast.makeText(application, t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }
}