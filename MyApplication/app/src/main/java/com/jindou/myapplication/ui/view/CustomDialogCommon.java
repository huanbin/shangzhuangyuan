package com.jindou.myapplication.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.jindou.myapplication.R;

import butterknife.ButterKnife;

public class CustomDialogCommon extends Dialog {

    public Context mContext;
    private int mLayoutId;
    private int mGravity;

    public CustomDialogCommon(Context context,int layoutId,int gravity, int style) {
        super(context,style);
        this.mContext=context;
        this.mLayoutId=layoutId;
        this.mGravity=gravity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mLayoutId);
        ButterKnife.bind(this);
    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity= mGravity;
        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height= WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }
    
}