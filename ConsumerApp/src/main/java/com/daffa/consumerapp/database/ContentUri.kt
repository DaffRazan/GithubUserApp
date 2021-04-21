package com.daffa.consumerapp.database

import android.net.Uri

class ContentUri {
    companion object {
        private const val AUTHORITY = "com.daffa.githubuserapp"
        private const val SCHEME = "content"
        private const val TABLE_NAME = "users_favorite"

        val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()
    }
}