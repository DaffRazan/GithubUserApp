package com.daffa.consumerapp.repository

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.daffa.consumerapp.database.ContentUri.Companion.CONTENT_URI
import com.daffa.consumerapp.database.UserEntity
import com.daffa.consumerapp.retrofitapi.RetrofitApi
import com.daffa.consumerapp.view.DetailUserActivity
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
        Log.d("setFavoriteUser", "display : $CONTENT_URI")
    }

    fun deleteFavoriteUser(id: Int, context: Context) {
        uriWithId = Uri.parse("$CONTENT_URI/$id")
        context.contentResolver.delete(uriWithId, null, null)
        Log.d("deleteFavoriteUser", "display : $uriWithId")
    }

    fun setFavoriteById(id: Int, context: Context) {
        uriWithId = Uri.parse("$CONTENT_URI/$id")
        val cursor = context.contentResolver.query(uriWithId, null, null, null, null)
        favoriteUser.postValue(cursor)
        Log.d("setFavoriteById", "display : $uriWithId")
    }

    fun getFavoriteById(): LiveData<Cursor> {
        return favoriteUser
    }

    fun getDetails(): LiveData<UserEntity> {
        return userDetailData
    }

}