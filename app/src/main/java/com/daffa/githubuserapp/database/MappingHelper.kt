package com.daffa.githubuserapp.database

import android.content.ContentValues
import android.database.Cursor

class MappingHelper {
    companion object {
        fun convertFromContentValues(contentValues: ContentValues): UserEntity {
            val userEntity: UserEntity

            val id = contentValues.getAsInteger("id")
            val avatarUrl = contentValues.getAsString("avatar_url")
            val login = contentValues.getAsString("login")
            val name = contentValues.getAsString("name")
            val type = contentValues.getAsString("type")
            val company = contentValues.getAsString("company")
            val location = contentValues.getAsString("location")
            val followers = contentValues.getAsInteger("followers")
            val following = contentValues.getAsInteger("following")
            val repos = contentValues.getAsInteger("public_repos")

            userEntity = UserEntity(
                id,
                avatarUrl,
                type,
                login,
                name,
                company,
                repos,
                location,
                followers,
                following
            )
            return userEntity
        }

        fun convertToContentValues(userEntity: UserEntity): ContentValues {
            val values = ContentValues()

            values.put("id", userEntity.id)
            values.put("avatar_url", userEntity.avatar_url)
            values.put("type", userEntity.type)
            values.put("login", userEntity.login)
            values.put("name", userEntity.name)
            values.put("company", userEntity.company)
            values.put("public_repos", userEntity.public_repos)
            values.put("location", userEntity.location)
            values.put("followers", userEntity.followers)
            values.put("following", userEntity.following)

            return values
        }

        fun mapCursorToArrayList(favoritesCursor: Cursor?): ArrayList<UserEntity> {
            val favoriteUserList = ArrayList<UserEntity>()

            favoritesCursor?.apply {
                while (moveToNext()) {
                    val id = getInt(getColumnIndexOrThrow("id"))
                    val avatarUrl = getString(getColumnIndexOrThrow("avatar_url"))
                    val type = getString(getColumnIndexOrThrow("type"))
                    val login = getString(getColumnIndexOrThrow("login"))
                    val name = getString(getColumnIndexOrThrow("name"))
                    val company = getString(getColumnIndexOrThrow("company"))
                    val location = getString(getColumnIndexOrThrow("location"))
                    val followers = getInt(getColumnIndexOrThrow("followers"))
                    val following = getInt(getColumnIndexOrThrow("following"))
                    val repos = getInt(getColumnIndexOrThrow("public_repos"))

                    favoriteUserList.add(
                        UserEntity(
                            id,
                            avatarUrl,
                            type,
                            login,
                            name,
                            company,
                            repos,
                            location,
                            followers,
                            following
                        )
                    )
                }
            }

            return favoriteUserList
        }
    }
}