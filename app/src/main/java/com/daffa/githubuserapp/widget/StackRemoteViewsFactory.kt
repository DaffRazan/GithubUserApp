package com.daffa.githubuserapp.widget

import android.content.Context
import android.database.Cursor
import android.graphics.BitmapFactory
import android.os.Binder
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.daffa.githubuserapp.R
import com.daffa.githubuserapp.database.MappingHelper
import com.daffa.githubuserapp.database.UserEntity
import com.daffa.githubuserapp.database.UserProvider
import java.net.URL

internal class StackRemoteViewsFactory(private val mContext: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private var mList: List<UserEntity> = listOf()
    private var cursor: Cursor? = null

    override fun onCreate() {

    }

    override fun onDataSetChanged() {
        cursor?.close()
        val resetIdentity = Binder.clearCallingIdentity()
        cursor = mContext.contentResolver.query(UserProvider.CONTENT_URI, null, null, null, null)

        val favoriteListData = MappingHelper.mapCursorToArrayList(cursor)
        mList = favoriteListData.toList()

        Log.d("widgetUsers", "Data = $mList")
        Binder.restoreCallingIdentity(resetIdentity)
    }

    override fun onDestroy() {
        mList = listOf()
        cursor?.close()
    }

    override fun getCount(): Int = mList.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)

        if (!mList.isNullOrEmpty()) {
            //bind data to stack
            rv.apply {
                mList[position].apply {
                    val imageBitmap = BitmapFactory.decodeStream(
                        URL(avatar_url).openConnection().getInputStream()
                    )
                    setImageViewBitmap(R.id.imageView_widget, imageBitmap)
                    setTextViewText(R.id.username_widget, login)
                    setTextViewText(R.id.type_widget, type)
                }
            }
        }
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = 0

    override fun hasStableIds(): Boolean = false
}