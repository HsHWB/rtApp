package com.example.sliding.mainview.Service;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by huehn on 16/2/16.
 */
public class EndActivityBroadCast extends BroadcastReceiver {
    private Activity activity;

    public EndActivityBroadCast(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String tag = intent.getStringExtra("activity");
        if (tag.equals("finish activity")) {
            activity.finish();
        }
    }
}
