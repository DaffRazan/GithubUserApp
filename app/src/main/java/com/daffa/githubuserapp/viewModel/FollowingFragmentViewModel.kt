package com.daffa.githubuserapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.daffa.githubuserapp.retrofitapi.model.User
import com.daffa.githubuserapp.repository.FollowingFragmentRepository

class FollowingFragmentViewModel(application: Application): AndroidViewModel(application) {
    private val repository = FollowingFragmentRepository(application)
    val listFollowing: LiveData<List<User>>
    val progressBar: LiveData<Boolean>

    // Communicate VM with repository
    init {
        this.listFollowing = repository.listFollowing
        this.progressBar = repository.progressBar
    }

    fun getFollowingModel(username: String){
        repository.getFollowing(username)
    }
}