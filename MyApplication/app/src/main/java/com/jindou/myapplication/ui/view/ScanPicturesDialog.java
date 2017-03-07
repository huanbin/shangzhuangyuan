package com.jindou.myapplication.ui.view;

import android.app.Dialog;
import android.app.VoiceInteractor;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.Loader;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jindou.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/25.
 */

public class ScanPicturesDialog extends Dialog {

    public Context context;
    private FragmentManager fm;
    private List<String> urls;
    private List<ImageView> views;
    private int currentIndex;

    public ScanPicturesDialog(Context context, FragmentManager fm,List<String> urls,int currentIndex) {
        super(context, R.style.ScanPicturesDialog);
        this.context = context;
        this.fm = fm;
        this.urls=urls;
        this.currentIndex=currentIndex;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.scan_picture_dialog_view);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPagerScanPicture);
        viewPager.setOffscreenPageLimit(1);
        ImageView ivSavePicture= (ImageView) findViewById(R.id.ivSavePicture);
        ivSavePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"hehe...",Toast.LENGTH_SHORT).show();
            }
        });
        final TextView tvCurrentIndex= (TextView) findViewById(R.id.tvCurrentIndex);
        views=new ArrayList<>();
        for (int i = 0; i < urls.size(); i++) {
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            if (i % 2 == 0) {
                imageView.setImageResource(R.drawable.adviseimage);
            } else {
                imageView.setImageResource(R.drawable.singleimage);
            }
//            Glide.with(context).load(urls.get(arg1)).crossFade().into(views.get(arg1));
            views.add(imageView);
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                String text=String.format("%d/%d",position,urls.size());
                tvCurrentIndex.setText(text);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter() {

            @Override
            //获取当前窗体界面数
            public int getCount() {
                // TODO Auto-generated method stub
                return views.size();
            }

            @Override
            //断是否由对象生成界面
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            //是从ViewGroup中移出当前View
            public void destroyItem(View arg0, int arg1, Object arg2) {
                ((ViewPager) arg0).removeView(views.get(arg1));
            }

            //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
            public Object instantiateItem(View arg0, int arg1) {
                ((ViewPager) arg0).addView(views.get(arg1));
                return views.get(arg1);
            }
        };
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setCurrentItem(currentIndex);
    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }
}
