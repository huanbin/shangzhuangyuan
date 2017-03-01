package com.jindou.myapplication.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

import com.jindou.myapplication.R;

import java.lang.reflect.Field;

/**
 * Created by zhishi on 2017/2/23.
 * 此类主要是解决EditText 通过android:textCursorDrawable属性，修改光标颜色，在华为手机上无效
 */
//华为手机无效解决方案（尝试还是无效）
public class MyCursorEditText extends EditText {

    public MyCursorEditText(Context context) {
        super(context);
    }

    public MyCursorEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        modifyCursorDrawable(context,attrs);
    }

    public MyCursorEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        modifyCursorDrawable(context,attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyCursorEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        modifyCursorDrawable(context,attrs);
    }

    private void modifyCursorDrawable(Context context, AttributeSet attrs){
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyCursorEditText);
        int drawable = a.getResourceId(R.styleable.MyCursorEditText_textCursorDrawable,0);
        if(drawable != 0) {
            try {
                Field setCursor = TextView.class.getDeclaredField("mCursorDrawableRes");
                setCursor.setAccessible(true);
                setCursor.set(this, drawable);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        a.recycle();
    }
}