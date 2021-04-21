package com.daffa.githubuserapp.repository

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.daffa.githubuserapp.database.UserEntity
import com.daffa.githubuserapp.database.UserProvider.Companion.CONTENT_URI
import com.daffa.githubuserapp.retrofitapi.RetrofitApi
import com.daffa.githubuserapp.view.DetailUserActivity
import com.daffa.githubuserapp.widget.FavoriteUsersWidget
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivityRepository(val application: Application) {
    val userDetailData = MutableLiveData<UserEntity>()
    val progressBar = MutableLiveData<Boolean>()
    val detailUserActivity = DetailUserActivity()
    private lateinit var uriWithId: Uri
    val favoriteUser = MutableLiveData<Cursor>()

    fun getUser(username: String) {
        // Show loading when accessing API
        progressBar.value = true

        // Network call
        RetrofitApi.API_CLIENT.userDetail(username).enqueue(object : Callback<UserEntity> {
            override fun onResponse(
                call: Call<UserEntity>,
                response: Response<UserEntity>
            ) {
                userDetailData.value = response.body()
                progressBar.value = false
            }

            override fun onFailure(call: Call<UserEntity>, t: Throwable) {
                progressBar.value = false
                detailUserActivity.getToastOnFailure()
            }

        })
    }

    fun setFavoriteUser(users_favorite: ContentValues, context: Context) {
        context.contentResolver.insert(CONTENT_URI, users_favorite)
        refreshWidgetDataUsers(application)
    }

    fun deleteFavoriteUser(id: Int, context: Context) {
        uriWithId = Uri.parse("$CONTENT_URI/$id")
        context.contentResolver.delete(uriWithId, null, null)
        refreshWidgetDataUsers(application)
    }

    fun setFavoriteById(id: Int, context: Context) {
        uriWithId = Uri.parse("$CONTENT_URI/$id")
        val cursor = context.contentResolver.query(uriWithId, null, null, null, null)
        favoriteUser.postValue(cursor)
    }

    //refresh Widget Data
    private fun refreshWidgetDataUsers(application: Application) {
        // Refresh data in UserWidget
        FavoriteUsersWidget.refreshDataUsersWidget(application)
    }

}