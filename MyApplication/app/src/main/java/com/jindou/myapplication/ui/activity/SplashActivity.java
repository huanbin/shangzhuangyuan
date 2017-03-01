package com.jindou.myapplication.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jindou.myapplication.application.MyApplication;
import com.jindou.myapplication.ui.util.SpUtil;

import java.sql.Time;

import timber.log.Timber;

public class SplashActivity extends AppCompatActivity {

    private boolean isFirstStart;
    public static final String KEY="isFirstStart";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = SpUtil.getInstance(this);
        SharedPreferences.Editor edit = sp.edit();
        isFirstStart = sp.getBoolean(KEY, false);

        if (!isFirstStart) {
            isFirstStart=true;
            Timber.d("first start app...store data");
            edit.putBoolean(KEY,isFirstStart).commit();
            TaskStackBuilder.create(this)
                    .addNextIntentWithParentStack(new Intent(this, MainActivity.class))
                    .addNextIntent(new Intent(this, WelcomeActivity.class))
                    .startActivities();
            Timber.d("first start app...");
        }else {
            TaskStackBuilder.create(this).addNextIntent(new Intent(this, MainActivity.class)).startActivities();
            Timber.d("not first start app...");
        }
    }
}
