package com.jindou.myapplication.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.jindou.myapplication.R;
import com.jindou.myapplication.ui.activity.user.LoginActivity;
import com.jindou.myapplication.ui.fragment.ShangwenFragment;
import com.jindou.myapplication.ui.fragment.MyFragment2;
import com.jindou.myapplication.ui.fragment.MyFragment3;
import com.jindou.myapplication.ui.fragment.MyFragment4;
import com.jindou.myapplication.ui.util.UiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * toolbar 48dp
 * common title 48dp
 * mdpi
 */
public class MainActivity extends AppCompatActivity implements ShangwenFragment.IDrawerListener{
    public static final String KEY_NAV_LEFT="imgLeft";
    public static final String KEY_NAV_NAME="name";
    public static final String KEY_NAV_RIGHT="imgRight";
//    R.drawable.radar_gold
    private static final int[] NAV_IMG_LEFT=new int[]{R.drawable.radar_gold,R.drawable.fav_collection,R.drawable.download,R.drawable.suggesst,R.drawable.aboutus};
    private static final int[] NAV_NAMES=new int[]{R.string.nav_leida,R.string.nav_fav,R.string.nav_download,R.string.nav_suggest,R.string.nav_aboutus};
    private static final int[] NAV_IMG_RIGHT=new int[]{R.drawable.arrow_right,R.drawable.arrow_right,R.drawable.float_button,R.drawable.arrow_right,R.drawable.arrow_right};
    private static final int[] TAB_IMG_SELECTED=new int[]{R.drawable.tab_shangwen_selected,R.drawable.tab_shangzhao_selected,R.drawable.tab_shangji_selected,R.drawable.tab_xinzhibao_selected};

    //    private TabLayout mTablayout;
//    private ViewPager mVierPager;
    @BindView(R.id.my_drawer_layout)
    public DrawerLayout mDrawerLayout;
    @BindView(R.id.tab_swen)
    public TextView mTabShangWen;
    @BindView(R.id.tab_szhao)
    public TextView mTabShangZhao;
    @BindView(R.id.tab_sji)
    public TextView mTabShangJi;
    @BindView(R.id.tab_xzhibao)
    public TextView mTabXinZhiBao;
//    @BindView(R.id.nav_listView)
//    public ListView mNavListView;
    @BindView(R.id.userPlane)
    public RelativeLayout mUserPlane;
    @BindView(R.id.ly_radar)
    public LinearLayout lyRadar;
    @BindView(R.id.ly_fav)
    public LinearLayout lyFav;
    @BindView(R.id.ib_download)
    public ImageButton mImageButton;
    @BindView(R.id.ly_suggest)
    public LinearLayout lySuggest;
    @BindView(R.id.ly_aboutus)
    public LinearLayout lyAbountus;

    private ShangwenFragment fragment1;
    private MyFragment2 fragment2;
    private MyFragment3 fragment3;
    private MyFragment4 fragment4;
    private FragmentManager fragmentManager;

    private boolean isSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        沉浸式状态栏
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.bg_app_bar),true);
        ButterKnife.bind(this);
//        全屏滑动DrawerLayout
        UiUtils.setDrawerLeftEdgeSize(this,mDrawerLayout,0.5f);
        fragmentManager = getSupportFragmentManager();
        //默认选中第一个tab
        setTabSelection(mTabShangWen,0);
        //测试
        String cachedirectory = Environment.getExternalStorageDirectory().getAbsolutePath();
        Timber.d("cachedirectory:"+cachedirectory);
    }
    /**
     * Tab点击事件
     * @param v
     */
    @OnClick({R.id.tab_swen,R.id.tab_szhao,R.id.tab_sji,R.id.tab_xzhibao})
    public void onTabClick(TextView v) {
        switch (v.getId()) {
            case R.id.tab_swen:
                setTabSelection(v,0);
                break;
            case R.id.tab_szhao:
                setTabSelection(v,1);
                break;
            case R.id.tab_sji:
                setTabSelection(v,2);
                break;
            case R.id.tab_xzhibao:
                setTabSelection(v,3);
                break;
            default:
                setTabSelection(v,0);
                break;
        }
    }
    @OnClick(R.id.userPlane)
    public void goLoginOrUserInfo(View view){
        Timber.d("you clicked mUserPlane");
        /*关闭导航栏*/
        mDrawerLayout.closeDrawer(GravityCompat.START);
        /*跳转登陆或用户详情页*/
        if (true) {
            startActivity(new Intent(this,LoginActivity.class));
        }
    }

    @OnClick({R.id.ly_radar,R.id.ly_fav,R.id.ly_suggest,R.id.ly_aboutus})
    public void onClickedDrawerItem(View pView){
        switch (pView.getId()) {
            case R.id.ly_radar:
                break;
            case R.id.ly_fav:
                break;
            case R.id.ly_suggest:
                break;
            case R.id.ly_aboutus:
                break;
            default:
                break;
        }
        Toast.makeText(MainActivity.this,"you clicked drawer item.",Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.ib_download)
    public void toggle(View pView){
        ImageButton imageButton= (ImageButton) pView;
        isSelected=!isSelected;
        if (isSelected) {
            imageButton.setImageResource(R.drawable.float_button_selected);
        }else {
            imageButton.setImageResource(R.drawable.float_button);
        }
    }

    /**
     * 处理选中tab
     * Fragmemnt动画效果
     * @param i
     */
    private void setTabSelection(TextView tv,int i){
        clearSelection(tv);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        switch (i) {
            case 0:
                if (fragment1 == null) {
                    fragment1 = new ShangwenFragment();
                    fragmentTransaction.add(R.id.container, fragment1);
                } else {
                    fragmentTransaction.show(fragment1);
                }
                break;
            case 1:
                if (fragment2 == null) {
                    fragment2 = MyFragment2.newInstance("Tab2...");
                    fragmentTransaction.add(R.id.container, fragment2);
                } else {
                    fragmentTransaction.show(fragment2);
                }
                break;
            case 2:
                if (fragment3 == null) {
                    fragment3 = MyFragment3.newInstance("Tab3...");
                    fragmentTransaction.add(R.id.container, fragment3);
                } else {
                    fragmentTransaction.show(fragment3);
                }
                break;
            case 3:
                if (fragment4 == null) {
                    fragment4 = MyFragment4.newInstance("Tab4...");
                    fragmentTransaction.add(R.id.container, fragment4);
                } else {
                    fragmentTransaction.show(fragment4);
                }
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
        //选中
        setTab(tv,getResources().getDrawable(TAB_IMG_SELECTED[i]));
        setTabTextColor(tv,R.color.tab_select_text_color);
    }
    //重置tab的状态（tab未选中状态）
    private void clearSelection(TextView tv) {
        //清除
        setTab(mTabShangWen,getResources().getDrawable(R.drawable.tab_shangwen));
        setTab(mTabShangZhao,getResources().getDrawable(R.drawable.tab_shangzhao));
        setTab(mTabShangJi,getResources().getDrawable(R.drawable.tab_shangji));
        setTab(mTabXinZhiBao,getResources().getDrawable(R.drawable.tab_xinzhibao));
        setTabTextColor(mTabShangWen,R.color.tab_unselect_text_color);
        setTabTextColor(mTabShangZhao,R.color.tab_unselect_text_color);
        setTabTextColor(mTabShangJi,R.color.tab_unselect_text_color);
        setTabTextColor(mTabXinZhiBao,R.color.tab_unselect_text_color);
    }

    private void setTabTextColor(TextView textView,int colorId){
        int color = this.getResources().getColor(colorId);
        textView.setTextColor(color);
    }
    private void setTab(TextView tv,Drawable image){
        tv.setCompoundDrawablePadding(8);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());//非常重要，必须设置，否则图片不会显示
        tv.setCompoundDrawables(null,image, null, null);
    }

    //重置fragment状态
    private void hideFragments(FragmentTransaction transaction) {
        // TODO Auto-generated method stub
        if (fragment1 != null) {
            transaction.hide(fragment1);
        }
        if (fragment2 != null) {
            transaction.hide(fragment2);
        }
        if (fragment3 != null) {
            transaction.hide(fragment3);
        }
        if (fragment4 != null) {
            transaction.hide(fragment4);
        }
    }


    @Override
    public void openDrawer() {
        if (mDrawerLayout!=null&&!mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void closeDrawer() {

    }

    @Override
    public void enableScroll(boolean scrollable) {
        if (mDrawerLayout!=null&&!scrollable) {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }else {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }
}
