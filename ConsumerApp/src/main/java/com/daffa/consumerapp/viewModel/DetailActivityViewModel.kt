package com.daffa.consumerapp.viewModel

import android.app.Application
import android.content.Context
import android.database.Cursor
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.daffa.consumerapp.database.UserEntity
import com.daffa.consumerapp.repository.DetailActivityRepository

class DetailActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val detailActivityrepository = DetailActivityRepository(application)
    val userDetailData: LiveData<UserEntity>
    val progressBar: LiveData<Boolean>
    private val favoriteUser: LiveData<Cursor>

    // Communicate VM with repository
    init {
        this.userDetailData = detailActivityrepository.userDetailData
        this.favoriteUser = detailActivityrepository.favoriteUser
        this.progressBar = detailActivityrepository.progressBar
    }

    fun getUserModel(username: String) {
        detailActivityrepository.getUser(username)
    }

    fun setFavoriteById(id: Int, context: Context) {
        detailActivityrepository.setFavoriteById(id, context)
    }

    fun getFavoriteById(): LiveData<Cursor> {
        return favoriteUser
    }

    fun getDetails(): LiveData<UserEntity> {
        return userDetailData
    }
}