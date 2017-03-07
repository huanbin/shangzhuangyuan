package com.jindou.myapplication.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jindou.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhishi on 2017/3/7.
 */

public class DynamicShareDialog extends Dialog {

    @BindView(R.id.containerLayouyt)
    LinearLayout containerLayouyt;
    private LinearLayout container;
    private Context context;
    private View customView;

    public DynamicShareDialog(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public DynamicShareDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        initView();
    }

    protected DynamicShareDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        customView = inflater.inflate(R.layout.dynamic_dialog_layout, null, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(customView);
        setContainerBackground("#FFF0F0F0");
    }

    public void setContainerBackground(String color) {
        View root = findViewById(android.R.id.content);
        root.setBackgroundColor(Color.parseColor(color));
    }

    public void setContainerMargin(int left, int top, int right, int bottom) {
        if (container != null) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) container.getLayoutParams();
            layoutParams.setMargins(left, top, right, bottom);
        }
    }

    public void addShareLineItem(LinearLayout container, String itemName) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(24, 0, 24, 0);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);

        ImageView imageView = new ImageView(context);
        LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(imageLayoutParams);
        imageView.setImageResource(R.drawable.mobileqq);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        linearLayout.addView(imageView);

        TextView textView = new TextView(context);
        LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(textLayoutParams);
        textLayoutParams.setMargins(0, 6, 0, 0);
        textView.setText(itemName);
        textView.setTextColor(Color.parseColor("#FF333333"));
        textView.setTextSize(10);
        textView.setGravity(Gravity.CENTER);
        linearLayout.addView(textView);

        container.addView(linearLayout);
    }

    public void addShareItem(LinearLayout container) {
        addShareLineItem(container, "test1");
        addShareLineItem(container, "test2");
        addShareLineItem(container, "test3");
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
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }
}
