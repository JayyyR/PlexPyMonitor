package com.joeracosta.joe.plexpymonitor;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by jacosta on 12/31/16.
 */

public class PyApp extends Application {
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
            LeakCanary.install(this);
        }
    }
}
