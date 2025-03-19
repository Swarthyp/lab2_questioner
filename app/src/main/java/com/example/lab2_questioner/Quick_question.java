package com.example.lab2_questioner;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class Quick_question extends AppWidgetProvider {

    private static final String YES_ACTION = "com.example.lab2_questioner.YES_ACTION";
    private static final String NO_ACTION = "com.example.lab2_questioner.NO_ACTION";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.quick_question);

        // Create PendingIntent for "Yes" button
        Intent yesIntent = new Intent(context, Quick_question.class);
        yesIntent.setAction(YES_ACTION);
        PendingIntent yesPendingIntent = PendingIntent.getBroadcast(context, 0, yesIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.widget_yes_button, yesPendingIntent);

        // Create PendingIntent for "No" button
        Intent noIntent = new Intent(context, Quick_question.class);
        noIntent.setAction(NO_ACTION);
        PendingIntent noPendingIntent = PendingIntent.getBroadcast(context, 1, noIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.widget_no_button, noPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (YES_ACTION.equals(intent.getAction())) {
            // Handle "Yes" click
            updateTextView(context, "Nice One ");
        } else if (NO_ACTION.equals(intent.getAction())) {
            // Handle "No" click
            updateTextView(context, "You have to do it now!");
        }
    }

    private static void updateTextView(Context context, String text) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, Quick_question.class));

        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.quick_question);
            views.setTextViewText(R.id.widget_text_view, text);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}