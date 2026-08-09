package com.roulete.chastity

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews

class LockWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray,
    ) {
        appWidgetIds.forEach { appWidgetId ->
            updateWidget(context, appWidgetManager, appWidgetId)
        }
    }
}

internal fun updateWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
    val state = AppStore.load(context)
    val now = System.currentTimeMillis()
    val isLocked = state.lockedUntilMillis > now

    val hoursLocked: Long = if (isLocked) {
        (now - state.lockedSinceMillis) / (1000L * 60 * 60)
    } else {
        0L
    }

    val hoursText = if (isLocked) {
        "${hoursLocked}+ time denied"
    } else {
        "Free 🔓"
    }

    val views = RemoteViews(context.packageName, R.layout.widget_lock)
    views.setTextViewText(R.id.widget_hours_text, hoursText)

    appWidgetManager.updateAppWidget(appWidgetId, views)
}
