package com.udacity.learning.blockbusters;

import android.app.Application;
import android.content.Context;
import android.util.Log;


import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by subbu on 7/22/16.
 */
public class BlockBustersApp extends Application {
    private static final String TAG = BlockBustersApp.class.getSimpleName();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }


    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate()");
        super.onCreate();
        Fabric.with(this, new Crashlytics());
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
