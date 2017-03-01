package com.jindou.myapplication.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/2/25.
 */

public class PicturePagerAdapter extends PagerAdapter{
    private List<View> views;
    public PicturePagerAdapter(List<View> pListImgs) {
        this.views=pListImgs;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position % views.size()), 0);
        return views.get(position % views.size());
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position % views.size()));
    }
}
