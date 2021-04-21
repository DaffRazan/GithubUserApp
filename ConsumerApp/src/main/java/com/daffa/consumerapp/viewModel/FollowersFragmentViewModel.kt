package com.daffa.consumerapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.daffa.consumerapp.repository.FollowersFragmentRepository
import com.daffa.consumerapp.retrofitapi.model.User

class FollowersFragmentViewModel(application: Application): AndroidViewModel(application) {
    private val repository = FollowersFragmentRepository(application)
    val listFollowers: LiveData<List<User>>
    val progressBar: LiveData<Boolean>

    // Communicate VM with repository
    init {
        this.listFollowers = repository.listFollowers
        this.progressBar = repository.progressBar
    }

    fun getFollowersModel(username: String){
        repository.getFollowers(username)
    }
}