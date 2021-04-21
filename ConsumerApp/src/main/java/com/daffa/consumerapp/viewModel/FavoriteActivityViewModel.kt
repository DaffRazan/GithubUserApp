package com.daffa.consumerapp.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daffa.consumerapp.database.ContentUri.Companion.CONTENT_URI
import com.daffa.consumerapp.database.MappingHelper
import com.daffa.consumerapp.database.UserEntity

class FavoriteActivityViewModel : ViewModel() {

    private val favoriteListUser = MutableLiveData<ArrayList<UserEntity>>()

    fun setFavoriteListUser(context: Context) {
        val cursor = context.contentResolver.query(CONTENT_URI, null, null, null, null)
        val favoriteListData = MappingHelper.mapCursorToArrayList(cursor)
        favoriteListUser.postValue(favoriteListData)
    }

    fun getListFavorite(): LiveData<ArrayList<UserEntity>> {
        return favoriteListUser
    }
}