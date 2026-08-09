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

    val elapsedMillis = (now - state.lockedSinceMillis).coerceAtLeast(0L)
    val hoursLocked = elapsedMillis / 3_600_000L
    val daysLocked = elapsedMillis / 86_400_000L

    val hoursText = when {
        !isLocked -> "Free 🔓"
        daysLocked >= 1L -> if (daysLocked == 1L) "1 day denied" else "$daysLocked days denied"
        hoursLocked == 1L -> "1 hour denied"
        else -> "$hoursLocked hours denied"
    }

    val views = RemoteViews(context.packageName, R.layout.widget_lock)
    views.setTextViewText(R.id.widget_hours_text, hoursText)

    appWidgetManager.updateAppWidget(appWidgetId, views)
}
