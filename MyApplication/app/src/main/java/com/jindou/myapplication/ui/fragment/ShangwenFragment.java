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
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhishi on 2017/2/17.
 */

public class ShangWenFragment extends Fragment {
    public static String FAV_TYPE="FAV_TYPE";
    private String[] titles = new String[]{"互联网金融", "电商", "创投", "财经", "房地产", "娱乐", "国内新闻", "国际新闻"};
    private AppCompatActivity activity;
    private IDrawerListener iDrawerListener;

//    @BindView(R.id.toolbar_radar)
//    public ImageButton ibRadar;
    @BindView(R.id.viewPager)
    public ViewPager viewPager ;
    @BindView(R.id.viewpagertab)
    public SmartTabLayout tabLayout;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.imgFavmanager)
    public ImageButton imgFavmanager;

    //    @BindView(R.id.toolbar_search)
//    public ImageButton ibSearch;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shangwen, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        activity = (AppCompatActivity) getActivity();
        iDrawerListener = (IDrawerListener) activity;
        initToolbar();
        initViewPager();
    }

    private void initViewPager() {
        //ViewPager
        viewPager.setAdapter(new FragmentStatePagerAdapter(activity.getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position>=0) {
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

        tabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        tabLayout.setViewPager(viewPager);
    }

    private void initToolbar() {
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iDrawerListener != null) {
                    iDrawerListener.openDrawer();
                }
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(activity, "you clicked MenuItem.", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    //    R.id.toolbar_search
    @OnClick({R.id.imgFavmanager})
//    @OnClick({R.id.toolbar_radar,R.id.imgFavmanager})
    public void clickedToolbarMenu(View pView){
        int id = pView.getId();
//        if (id==R.id.toolbar_radar) {
//            Timber.d("you clicked radar.");
//        } else
        if (id==R.id.imgFavmanager) {
            Intent intent = new Intent(activity, FavManagerActivity.class);
            intent.putExtra(ShangWenFragment.FAV_TYPE,"shangwenFav");
            startActivity(intent);
        }
    }

    public interface IDrawerListener {
        void openDrawer();

        void closeDrawer();

        void enableScroll(boolean scrollable);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.sw_menu, menu);
//        //自定义SearchView的icon
//        SearchView mSearchView=(SearchView) menu
//                .findItem(R.id.action_search)
//                .getActionView();
//        ImageView searchButton = (ImageView)mSearchView.findViewById(R.id.search_button);
//        searchButton.setImageResource(R.drawable.search);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

}
