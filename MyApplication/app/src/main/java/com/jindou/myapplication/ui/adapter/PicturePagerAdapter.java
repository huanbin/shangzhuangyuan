package com.jindou.myapplication.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Administrator on 2017/2/25.
 */

public class PicturePagerAdapter extends PagerAdapter{
    private ImageView[] views;
    private Context context;
    private List<String> urls;
    public PicturePagerAdapter(Context context, List<String> urls) {
        this.context=context;
        this.urls=urls;
        views=new ImageView[urls.size()];
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView=views[position % views.length];
        container.addView(imageView, 0);
        Glide.with(context).load(urls.get(position)).into(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views[position % views.length]);
    }
}
