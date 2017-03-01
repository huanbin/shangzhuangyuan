package com.jindou.myapplication.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jindou.myapplication.ui.fragment.ImageFragment;

import java.util.List;

/**
 * Created by zhishi on 2017/3/1.
 */

public class FragmentImageAdapter extends FragmentPagerAdapter {

    private List<String> urls;
    private Context context;

    public FragmentImageAdapter(FragmentManager fm, Context context, List<String> urls) {
        super(fm);
        this.urls=urls;
        this.context=context;
    }


    @Override
    public Fragment getItem(int position) {
        return ImageFragment.newInstance(context,urls.get(position));
    }

    @Override
    public int getCount() {
        return urls.size();
    }
}