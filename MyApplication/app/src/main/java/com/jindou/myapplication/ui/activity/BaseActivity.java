package com.jindou.myapplication.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jindou.myapplication.R;
import butterknife.ButterKnife;
import qiu.niorgai.StatusBarCompat;

/**
 * Created by zhishi on 2017/2/16.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public BaseActivity mActivity;
    public abstract int getContentViewId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT==Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.bg_app_bar));
        }
        if (Build.VERSION.SDK_INT==Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        mActivity=this;
    }
}
