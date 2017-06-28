package com.szzh.audio.newviewpager.annotation;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.szzh.audio.newviewpager.application.NetApplication;

import java.lang.reflect.Method;

import static android.content.ContentValues.TAG;

/**
 * Created by jzz
 * on 2017/6/19.
 * <p>
 * desc:
 */

public class AnnitationProcess<T> {

    public void process(Class<T> clx) {

        Method[] declaredMethods = clx.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {

            NetAnnotation annotation = declaredMethod.getAnnotation(NetAnnotation.class);
            if (annotation != null) {
                // boolean haveNet = annotation.haveNet();
                // if (haveNet) {

                ConnectivityManager connectionService = (ConnectivityManager) NetApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetworkInfo = connectionService.getActiveNetworkInfo();

                if (activeNetworkInfo == null) {
                    Log.e(TAG, "process: ---1--网络不可用--->");
                    return;
                }

                boolean available = activeNetworkInfo.isAvailable();
                boolean connected = activeNetworkInfo.isConnected();

                if (available && connected) {
                    Log.e(TAG, "process: -------网络可用------>");
                } else {
                    Log.e(TAG, "process: ---2----网络不可用----->");
                }

                // }

            }
        }

    }
}
