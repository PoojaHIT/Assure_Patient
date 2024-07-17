package com.hit.assure.Util;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import androidx.multidex.MultiDex;

//import com.zoho.salesiqembed.ZohoSalesIQ;

public class MyApplication extends Application {

    public static final String TAG = "MyApplication";
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
//        ZohoSalesIQ.init(this, "cV%2Bbhf5mWBoek2tQYzZtiuO8N6%2B03%2FM3gYZJBXBTcLGLd%2F9k%2B6V9cUY8fh8hKB3U0QNYLjmHuON%2BuvXQuLm6j%2FHFxiSZF7tnJVLvXVRzRU0%3D_in",
//                "F3CL2WrKFFniJA8mSh%2FwrBkqPtsZFp15Y%2B8zpuKlNt%2Fv84lyDs68v%2FjMsW4ZuDEuxpwyKruEoc3K9nsh37TaZve%2BeN5ssCj7OJbfJkiglmB4FlMieZLGg8gP8SfKk76I9d8Hq2qFyKZ7M3KT9hAJ2X%2Br1YuhVUrRo5qHplDH3oChbGknBmkeuRq6mJYGYQFO" );

        mInstance = this;
        PreferenceServices.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    public static synchronized MyApplication getInstance() {
        return mInstance;
    }
}
