package com.example.agterra.pickster;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by Agterra on 15/07/2017.
 */

public class MyReceiver extends BroadcastReceiver {

    public static SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onReceive(Context context, Intent intent) {

        swipeRefreshLayout.setRefreshing(false);

        System.out.println("check");

    }
}
