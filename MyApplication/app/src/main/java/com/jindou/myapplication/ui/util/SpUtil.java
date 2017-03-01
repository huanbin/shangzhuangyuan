package com.jindou.myapplication.ui.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zhishi on 2017/3/1.
 */

public class SpUtil {
    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "share_szy_data";
    private static SharedPreferences sp;

    public static SharedPreferences getInstance(Context context){
        if (null==sp) {
            sp=context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }
        return sp;
    }
}
