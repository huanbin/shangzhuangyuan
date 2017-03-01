package com.jindou.myapplication.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.jindou.myapplication.R;

/**
 * Created by Administrator on 2017/2/25.
 */

public class ScanPicturesDialog extends Dialog {

    public Context context;
    private View customView;

    public ScanPicturesDialog(Context context) {
        super(context, R.style.ScanPicturesDialog);
        this.context = context;
        LayoutInflater inflater= LayoutInflater.from(context);
        customView = inflater.inflate(R.layout.fragment_scan_picture_dialog, null);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(customView);
    }
    @Override
    public View findViewById(int id) {
        return super.findViewById(id);
    }
    public View getCustomView() {
        return customView;
    }
    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity= Gravity.CENTER;
        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height= WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }
}
