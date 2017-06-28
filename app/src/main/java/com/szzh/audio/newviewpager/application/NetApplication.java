package com.szzh.audio.newviewpager.application;

import android.app.Application;

/**
 * Created by jzz
 * on 2017/6/19.
 * <p>
 * desc:
 */

public class NetApplication extends Application {

    private static NetApplication context;

    @Override
    public void onCreate() {
        super.onCreate();

        if (context == null)
            context = this;
    }

    public static NetApplication getContext() {
        return context;
    }
}
