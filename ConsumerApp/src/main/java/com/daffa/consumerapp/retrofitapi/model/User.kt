package com.daffa.consumerapp.retrofitapi.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val avatar_url: String?,
    val login: String? = null,
    val type: String? = null
) : Parcelable
