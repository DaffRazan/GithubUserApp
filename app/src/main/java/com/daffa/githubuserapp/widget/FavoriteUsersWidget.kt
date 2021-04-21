package com.daffa.githubuserapp.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.net.toUri
import com.daffa.githubuserapp.R
import com.daffa.githubuserapp.view.MainActivity

/**
 * Implementation of App Widget functionality.
 */
class FavoriteUsersWidget : AppWidgetProvider() {
    companion object {
        private fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            // Intent Widget
            val intent = Intent(context, StackWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

            // onClick
            val onclickIntent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val onClickPendingIntent = PendingIntent.getActivity(context, 0, onclickIntent, 0)

            // Set Views Widget
            val views = RemoteViews(context.packageName, R.layout.favorite_users_widget)
            views.setRemoteAdapter(R.id.stack_view, intent)
            views.setEmptyView(R.id.stack_view, R.id.empty_view)
            views.setTextViewText(R.id.banner_text, context.getString(R.string.favorite_users))
            views.setTextViewText(
                R.id.tv_userWidget_title,
                context.getString(R.string.favorite_users)
            )
            views.setTextViewText(
                R.id.tv_userWidget_message,
                context.getString(R.string.favorite_users)
            )
            views.setOnClickPendingIntent(R.id.banner_text, onClickPendingIntent)

            // update widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        fun refreshDataUsersWidget(context: Context) {
            val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE).apply {
                component = ComponentName(context, FavoriteUsersWidget::class.java)
            }
            context.sendBroadcast(intent)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        intent.let {
            if (it.action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {
                val componentName = ComponentName(context, FavoriteUsersWidget::class.java)
                AppWidgetManager.getInstance(context).notifyAppWidgetViewDataChanged(
                    AppWidgetManager.getInstance(context).getAppWidgetIds(componentName),
                    R.id.stack_view
                )
            }
        }
        super.onReceive(context, intent)
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }
}