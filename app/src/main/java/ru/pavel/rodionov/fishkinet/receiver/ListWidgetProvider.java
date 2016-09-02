package ru.pavel.rodionov.fishkinet.receiver;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.pavel.rodionov.fishkinet.R;
import ru.pavel.rodionov.fishkinet.remote_adapter.ListRemoteAdapter;


public class ListWidgetProvider extends AppWidgetProvider {

    public static final String ACTION_URLS = "ru.pavel.rodionov.fishkinet.ACTION_URLS";
    public static final String EXTRA_URLS = "ru.pavel.rodionov.fishkinet.EXTRA_URLS";
    public static final String EXTRA_POSITION = "ru.pavel.rodionov.fishkinet.EXTRA_POSITION";

    private List<String> urls = new ArrayList<>();


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        for(int appWidgetId:appWidgetIds){
            Intent intent = new Intent(context, ListRemoteAdapter.class);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            remoteViews.setRemoteAdapter(appWidgetId,R.id.widget_list_view,intent);

            Intent urlsIntent = new Intent(context,ListWidgetProvider.class);
            urlsIntent.setAction(ACTION_URLS);
            PendingIntent urlsPendingIntent = PendingIntent.getBroadcast(context,0,urlsIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setPendingIntentTemplate(R.id.widget_list_view,urlsPendingIntent);
        }

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,R.id.widget_list_view);
        appWidgetManager.updateAppWidget(appWidgetIds,remoteViews);

    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if(intent.getAction().equals(ACTION_URLS)){
            urls = intent.getExtras().getStringArrayList(EXTRA_URLS);
            int position = intent.getExtras().getInt(EXTRA_POSITION);

            Intent webIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(urls.get(position)));
            webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(webIntent);
        }

    }
}
