package ru.pavel.rodionov.fishkinet.remote_adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import ru.pavel.rodionov.fishkinet.R;


public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

    private List<String> mWidgetTitles = new ArrayList<>();

    private Context mContext;

    public ListRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        for(int i=0; i<20; i++){
            mWidgetTitles.add("item number: " + i);
        }
    }

    @Override
    public void onDataSetChanged() {

    }


    @Override
    public int getCount() {
        return mWidgetTitles.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        rv.setTextViewText(R.id.widget_item_title, mWidgetTitles.get(i));
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDestroy() {}
}