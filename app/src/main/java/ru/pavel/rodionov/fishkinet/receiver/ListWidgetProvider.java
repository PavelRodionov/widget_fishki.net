package ru.pavel.rodionov.fishkinet.receiver;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import ru.pavel.rodionov.fishkinet.R;
import ru.pavel.rodionov.fishkinet.remote_adapter.ListRemoteAdapter;


public class ListWidgetProvider extends AppWidgetProvider {



    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        for(int appWidgetId:appWidgetIds){
            Intent intent = new Intent(context, ListRemoteAdapter.class);
            //intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            remoteViews.setRemoteAdapter(appWidgetId,R.id.widget_list_view,intent);
        }

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,R.id.widget_list_view);
        appWidgetManager.updateAppWidget(appWidgetIds,remoteViews);

    }



}
