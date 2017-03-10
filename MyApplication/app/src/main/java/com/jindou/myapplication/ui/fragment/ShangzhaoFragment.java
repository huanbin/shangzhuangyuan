package com.jindou.myapplication.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.ImageButton;
import android.widget.Toast;

import com.jindou.myapplication.R;
import com.jindou.myapplication.ui.activity.FavManagerActivity;
import com.jindou.myapplication.ui.activity.SearchActivity;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;


public class ShangZhaoFragment extends Fragment {

    private String[] titles = new String[]{"市场营销", "项目管理", "人力资源", "法务管理", "人才管理培训"};

    private static final String ARG_PARAM1 = "param1";
    private AppCompatActivity activity;
    private ShangWenFragment.IDrawerListener iDrawerListener;

    @BindView(R.id.shangzhaoToolbar)
    public Toolbar shangzhaoToolbar;
    @BindView(R.id.shangzhaoTabLayout)
    public SmartTabLayout shangzhaoTabLayout;
    @BindView(R.id.shangzhaoViewPager)
    public ViewPager shangzhaoViewPager;
    @BindView(R.id.shangzhaoFavmanager)
    public ImageButton shangzhaoFavmanager;

    public ShangZhaoFragment() {
        // Required empty public constructor
    }

    public static ShangZhaoFragment newInstance() {
        ShangZhaoFragment fragment = new ShangZhaoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.shangzhaoFavmanager)
    public void onClickFavmanager(View view){
        Intent intent = new Intent(activity, FavManagerActivity.class);
        intent.putExtra(ShangWenFragment.FAV_TYPE,"shangzhaoFav");
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shangzhao, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        activity = (AppCompatActivity) getActivity();
        iDrawerListener = (ShangWenFragment.IDrawerListener) activity;
        initToolbar();
        initViewPager();
    }

    private void initToolbar() {
        activity.setSupportActionBar(shangzhaoToolbar);
        //关闭默认的title
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        shangzhaoToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(activity, "you clicked MenuItem.", Toast.LENGTH_SHORT).show();
                switch (item.getItemId()) {
                    case R.id.action_search:
                        Intent intent=new Intent(activity, SearchActivity.class);
                        intent.putExtra(ShangWenFragment.SEARCH_SOURCE,"商招搜索");
                        intent.putExtra(ShangWenFragment.SEARCH_TYPE,1);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }

                return true;
            }
        });
    }

    private void initViewPager() {
        //ViewPager
        shangzhaoViewPager.setAdapter(new FragmentStatePagerAdapter(activity.getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position >= 0) {
                    return ShangWenItemFragment.newInstance();
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

        shangzhaoTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        shangzhaoTabLayout.setViewPager(shangzhaoViewPager);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Timber.d("onCreateOptionsMenu");
        inflater.inflate(R.menu.sz_menu, menu);
    }
}
