package ru.pavel.rodionov.fishkinet.remote_adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import ru.pavel.rodionov.fishkinet.R;
import ru.pavel.rodionov.fishkinet.receiver.ListWidgetProvider;


public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

    private static final String URL_SITE = "http://fishki.net";
    private static final String URL_MSITE = "http://m.fishki.net";

    private ArrayList<String> mWidgetTitles = new ArrayList<>();
    private ArrayList<String> mWidgetUrls = new ArrayList<>();

    private Context mContext;


    public ListRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {}

    @Override
    public void onDataSetChanged() {
        mWidgetTitles.clear();
        mWidgetUrls.clear();
        try {
            Document doc = Jsoup.connect(URL_SITE).get();
            Elements titles = doc.select("h2.post_title");

            for(Element title:titles){
                Element tagA = title.getElementsByTag("a").first();

                String text = tagA.text().equals("") ? "Реклама" : tagA.text();
                String url = URL_MSITE + tagA.attr("href");
                
                mWidgetTitles.add(text);
                mWidgetUrls.add(url);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getCount() {
        return mWidgetTitles.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        rv.setTextViewText(R.id.widget_item_title, mWidgetTitles.get(i));

        Bundle extras = new Bundle();
        extras.putStringArrayList(ListWidgetProvider.EXTRA_URLS,mWidgetUrls);
        extras.putInt(ListWidgetProvider.EXTRA_POSITION,i);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.widget_item_title,fillIntent);

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
