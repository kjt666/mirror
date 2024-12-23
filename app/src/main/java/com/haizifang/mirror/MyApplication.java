package com.haizifang.mirror;

import android.app.Application;

import androidx.annotation.NonNull;

public class MyApplication extends Application {

    final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {

            }
        });
    }
}
