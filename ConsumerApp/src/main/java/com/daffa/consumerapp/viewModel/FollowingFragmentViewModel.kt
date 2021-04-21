package com.daffa.consumerapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.daffa.consumerapp.repository.FollowingFragmentRepository
import com.daffa.consumerapp.retrofitapi.model.User

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