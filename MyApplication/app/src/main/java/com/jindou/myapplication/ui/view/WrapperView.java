package com.jindou.myapplication.ui.view;

import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by zhishi on 2017/3/6.
 * add background transition animation
 * 注意传值，颜色值int类型，而不是颜色的引用类型int
 */

public class WrapperView {
    private View view;
    private int background;

    public WrapperView() {
    }

    public WrapperView(View view) {
        this.view = view;
    }

    public View getButton() {
        return view;
    }

    public void setButton(Button button) {
        this.view = view;
    }

    public int getBackground() {
        ColorDrawable colorDrawable= (ColorDrawable) view.getBackground();
        int color = colorDrawable.getColor();
        Log.d("TAG","get color="+color);
        return color;
    }

    public void setBackground(int background) {
        view.setBackgroundColor(background);
        this.background = background;
        Log.d("TAG","set color="+background);
    }
}
