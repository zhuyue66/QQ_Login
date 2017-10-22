package com.example.qq_login.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by zhuyue66 on 2017/10/22.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
