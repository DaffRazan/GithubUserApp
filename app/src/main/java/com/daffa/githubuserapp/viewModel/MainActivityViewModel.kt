package com.daffa.githubuserapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.daffa.githubuserapp.retrofitapi.model.User
import com.daffa.githubuserapp.repository.MainActivityRepository

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val mainActivityRepository = MainActivityRepository(application)

    // Live data for View
    val listUser: LiveData<List<User>>
    val progressBar : LiveData<Boolean>

    // Communicate VM with repository
    init {
        this.listUser = mainActivityRepository.listUser
        this.progressBar = mainActivityRepository.progressBar
    }

    fun searchUserModel(searchUser: String){
        mainActivityRepository.searchUser(searchUser)
    }
}