package com.daffa.consumerapp.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "users_favorite")
@Parcelize
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "avatar_url")
    var avatar_url: String? = null,

    @ColumnInfo(name = "type")
    var type: String? = null,

    @ColumnInfo(name = "login")
    var login: String? = null,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "company")
    var company: String? = null,

    @ColumnInfo(name = "public_repos")
    var public_repos: Int? = null,

    @ColumnInfo(name = "location")
    var location: String? = null,

    @ColumnInfo(name = "followers")
    var followers: Int? = null,

    @ColumnInfo(name = "following")
    var following: Int? = null
) : Parcelable