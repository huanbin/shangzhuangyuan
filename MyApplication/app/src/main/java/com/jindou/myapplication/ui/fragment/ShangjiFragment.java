package com.jindou.myapplication.ui.fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jindou.myapplication.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.security.PublicKey;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ShangjiFragment extends Fragment {

    private String[] titles = new String[]{"IT互联网", "交通/物流", "财政/金融", "纺织/制造","教育/培训"};
    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private AppCompatActivity activity;
    private ShangwenFragment.IDrawerListener iDrawerListener;

    @BindView(R.id.shangjiTabLayout)
    public SmartTabLayout shangjiTabLayout;
    @BindView(R.id.shangjiViewPager)
    public ViewPager shangjiViewPager;
    @BindView(R.id.shangjiToolbar)
    public Toolbar shangjiToolbar;
    @BindView(R.id.shangjiHuizhan)
    public TextView shangjiHuizhan;
    @BindView(R.id.shangjiData)
    public TextView shangjiData;
    @BindView(R.id.shangjiZhaoshang)
    public TextView shangjiZhaoshang;
    @BindView(R.id.shangjiZhaobiao)
    public TextView shangjiZhaobiao;
    @BindView(R.id.shangjiReport)
    public TextView shangjiReport;
    public ShangjiFragment() {
        // Required empty public constructor
    }

    public static ShangjiFragment newInstance(String param1) {
        ShangjiFragment fragment = new ShangjiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //必须设置（否则fragment不支持menu，无法显示）
        setHasOptionsMenu(true);
        activity = (AppCompatActivity) getActivity();
        iDrawerListener = (ShangwenFragment.IDrawerListener) activity;
        initToolbar();
        initViewPager();
        setCurrent(0);
    }

    private void initViewPager() {
        //ViewPager
        shangjiViewPager.setAdapter(new FragmentStatePagerAdapter(activity.getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position>=0) {
                    return ShangwenItemFragment.newInstance();
                }
                return MyFragment.newInstance(titles[position]);
            }

            @Override
            public int getCount() {
                return titles.length;
            }

            /**
             * indicator tab  title
             * @param position
             * @return
             */
            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });

        shangjiTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (iDrawerListener != null) {
                    if (position > 0) {
                        iDrawerListener.enableScroll(false);
                    } else {
                        iDrawerListener.enableScroll(true);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        shangjiTabLayout.setViewPager(shangjiViewPager);
    }

    private void initToolbar() {
        activity.setSupportActionBar(shangjiToolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        shangjiToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(activity, "you clicked MenuItem.", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shangji, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.sj_menu,menu);
    }

    @OnClick({R.id.shangjiHuizhan,R.id.shangjiData,R.id.shangjiZhaoshang,R.id.shangjiZhaobiao,R.id.shangjiReport})
    public void selectShangjiItem(View view){
        clearTextState();
        switch (view.getId()) {
            case R.id.shangjiHuizhan:
                setCurrent(0);
                break;
            case R.id.shangjiData:
                setCurrent(1);
                break;
            case R.id.shangjiZhaoshang:
                setCurrent(2);
                break;
            case R.id.shangjiZhaobiao:
                setCurrent(3);
                break;
            case R.id.shangjiReport:
                setCurrent(4);
                break;
            default:
                break;
        }
    }

    private void setCurrent(int position){
        if (0==position) {
            shangjiHuizhan.setBackgroundResource(R.drawable.shangji_button_selected);
            shangjiHuizhan.setTextColor(Color.parseColor("#FFFFFFFF"));
        } else if (1==position) {
            shangjiData.setBackgroundResource(R.drawable.shangji_button_selected);
            shangjiData.setTextColor(Color.parseColor("#FFFFFFFF"));
        } else if (2==position) {
            shangjiZhaoshang.setBackgroundResource(R.drawable.shangji_button_selected);
            shangjiZhaoshang.setTextColor(Color.parseColor("#FFFFFFFF"));
        } else if (3==position) {
            shangjiZhaobiao.setBackgroundResource(R.drawable.shangji_button_selected);
            shangjiZhaobiao.setTextColor(Color.parseColor("#FFFFFFFF"));
        } else if (4==position) {
            shangjiReport.setBackgroundResource(R.drawable.shangji_button_selected);
            shangjiReport.setTextColor(Color.parseColor("#FFFFFFFF"));
        }
    }

    private void clearTextState(){
        shangjiHuizhan.setBackgroundResource(R.drawable.shangji_button_normal);
        shangjiHuizhan.setTextColor(Color.parseColor("#FF333333"));
        shangjiData.setBackgroundResource(R.drawable.shangji_button_normal);
        shangjiData.setTextColor(Color.parseColor("#FF333333"));
        shangjiZhaoshang.setBackgroundResource(R.drawable.shangji_button_normal);
        shangjiZhaoshang.setTextColor(Color.parseColor("#FF333333"));
        shangjiZhaobiao.setBackgroundResource(R.drawable.shangji_button_normal);
        shangjiZhaobiao.setTextColor(Color.parseColor("#FF333333"));
        shangjiReport.setBackgroundResource(R.drawable.shangji_button_normal);
        shangjiReport.setTextColor(Color.parseColor("#FF333333"));
    }
}
