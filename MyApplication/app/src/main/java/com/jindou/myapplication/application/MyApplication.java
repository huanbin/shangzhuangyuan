package com.jindou.myapplication.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.jindou.myapplication.ui.util.SpUtil;

import timber.log.Timber;

/**
 * Created by zhishi on 2017/2/15.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
