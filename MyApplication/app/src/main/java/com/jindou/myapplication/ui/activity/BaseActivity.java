package com.jindou.myapplication.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;
import com.jindou.myapplication.R;


import butterknife.BindView;
import butterknife.ButterKnife;

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
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.bg_app_bar),true);
        mActivity=this;
    }
}
