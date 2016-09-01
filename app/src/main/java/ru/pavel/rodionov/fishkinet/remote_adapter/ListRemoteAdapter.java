package ru.pavel.rodionov.fishkinet.remote_adapter;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;


public class ListRemoteAdapter extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(getApplicationContext());
    }

}
