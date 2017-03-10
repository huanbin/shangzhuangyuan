package com.jindou.myapplication.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jindou.myapplication.R;
import com.jindou.myapplication.model.User;
import com.jindou.myapplication.ui.activity.user.LoginActivity;
import com.jindou.myapplication.ui.activity.user.UserInfoActivity;
import com.jindou.myapplication.ui.fragment.ShangWenFragment;
import com.jindou.myapplication.ui.fragment.ShangZhaoFragment;
import com.jindou.myapplication.ui.fragment.ShangJiFragment;
import com.jindou.myapplication.ui.fragment.XinZhiBaoFragment;
import com.jindou.myapplication.ui.util.ToastUtil;
import com.jindou.myapplication.ui.util.UiUtils;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * toolbar 48dp
 * common title 48dp
 * mdpi
 */
public class MainActivity extends BaseActivity implements ShangWenFragment.IDrawerListener {
    private static final int[] TAB_IMG_SELECTED = new int[]{R.drawable.tab_shangwen_selected, R.drawable.tab_shangzhao_selected, R.drawable.tab_shangji_selected, R.drawable.tab_xinzhibao_selected};
    private final String[] FRAGMENT_TAGS = new String[]{"ShangWenTag", "ShangZhaoTag", "ShangJiTag", "XinZhiBaoTag"};
    private int currentIndex = 0;
    private String INDEX_KEY = "currentFragment";
    //登录
    private int REQUET_LOGIN = 100;

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
    @BindView(R.id.userPlane)
    public RelativeLayout mUserPlane;
    @BindView(R.id.userIcon)
    public ImageView mUserIcon;
    @BindView(R.id.userName)
    public TextView mUserName;
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

    @BindString(R.string.login)
    String sLogin;

    private ShangWenFragment mShangWenFrag;
    private ShangZhaoFragment mShangZhaoFrag;
    private ShangJiFragment mShangJiFrag;
    private XinZhiBaoFragment mXinZhiBaoFrag;
    private FragmentManager mFragmentManager;

    private boolean isSelected;
    private boolean isLogin;
    private User mUser;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
////        沉浸式状态栏
//        if (Build.VERSION.SDK_INT==Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(getResources().getColor(R.color.bg_app_bar));
//        }
//        if (Build.VERSION.SDK_INT==Build.VERSION_CODES.M) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }
//        ButterKnife.bind(this);
        mFragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(INDEX_KEY);
            mShangWenFrag = (ShangWenFragment) mFragmentManager.findFragmentByTag(FRAGMENT_TAGS[0]);
            mShangZhaoFrag = (ShangZhaoFragment) mFragmentManager.findFragmentByTag(FRAGMENT_TAGS[1]);
            mShangJiFrag = (ShangJiFragment) mFragmentManager.findFragmentByTag(FRAGMENT_TAGS[2]);
            mXinZhiBaoFrag = (XinZhiBaoFragment) mFragmentManager.findFragmentByTag(FRAGMENT_TAGS[3]);
        }
//        全屏滑动DrawerLayout
        UiUtils.setDrawerLeftEdgeSize(this, mDrawerLayout, 0.5f);
        //默认选中第一个tab
        setTabSelection(mTabShangWen, 0);

//        Intent intent = getIntent();
//        if (intent!=null) {
//            String className = intent.getComponent().getClassName();
//            Timber.d("className=" + className);
//            mUser = (User) intent.getSerializableExtra(LoginActivity.USER_KEY);
//            if (mUser.getToken() != null) {
//                isLogin = true;
//            }
//        }

    }

    @Override
    public void handleIntent() {
        super.handleIntent();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent!=null) {
            String className = intent.getComponent().getClassName();
            Timber.d("className=" + className);
            mUser = (User) intent.getSerializableExtra(LoginActivity.USER_KEY);
            if (mUser !=null&& mUser.getToken() != null) {
                isLogin = true;
            }else {
                isLogin=false;
            }
        }
    }

    //保存Fragment状态恢复（防止内存不足等情况，程序在后台被杀死）
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(INDEX_KEY, currentIndex);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Timber.d("onResume isLogin=" + isLogin);
        //如果已登录
        if (isLogin) {
            mUserName.setText(mUser.getUsername());
        } else {
            mUserName.setText(sLogin);
        }
    }

    /**
     * Tab点击事件
     *
     * @param v
     */
    @OnClick({R.id.tab_swen, R.id.tab_szhao, R.id.tab_sji, R.id.tab_xzhibao})
    public void onTabClick(TextView v) {
        switch (v.getId()) {
            case R.id.tab_swen:
                setTabSelection(v, 0);
                break;
            case R.id.tab_szhao:
                setTabSelection(v, 1);
                break;
            case R.id.tab_sji:
                setTabSelection(v, 2);
                break;
            case R.id.tab_xzhibao:
                setTabSelection(v, 3);
                break;
            default:
                setTabSelection(v, 0);
                break;
        }
    }

    @OnClick(R.id.userPlane)
    public void goLoginOrUserInfo(View view) {
        Timber.d("you clicked mUserPlane");
        /*关闭导航栏*/
        mDrawerLayout.closeDrawer(GravityCompat.START);
        /*跳转登陆或用户详情页*/
        if (!isLogin) {
            startActivity(new Intent(this, LoginActivity.class));
//            用户可能去注册
//            startActivityForResult(new Intent(this, LoginActivity.class),REQUET_LOGIN);
        } else {
            //个人信息
            ToastUtil.show(this, "个人中心");
            Intent intent=new Intent(this, UserInfoActivity.class);
            intent.putExtra(LoginActivity.USER_KEY,mUser);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUET_LOGIN && resultCode == RESULT_OK && data != null) {
        }
    }

    @OnClick({R.id.ly_radar, R.id.ly_fav, R.id.ly_suggest, R.id.ly_aboutus})
    public void onClickedDrawerItem(View pView) {
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
        Toast.makeText(MainActivity.this, "you clicked drawer item.", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.ib_download)
    public void toggle(View pView) {
        ImageButton imageButton = (ImageButton) pView;
        isSelected = !isSelected;
        if (isSelected) {
            imageButton.setImageResource(R.drawable.float_button_selected);
        } else {
            imageButton.setImageResource(R.drawable.float_button);
        }
    }

    /**
     * 处理选中tab
     * Fragmemnt动画效果
     *
     * @param i
     */
    private void setTabSelection(TextView tv, int i) {
        currentIndex = i;
        clearSelection(tv);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        switch (i) {
            case 0:
                if (mShangWenFrag == null) {
                    mShangWenFrag = new ShangWenFragment();
                    fragmentTransaction.add(R.id.container, mShangWenFrag, FRAGMENT_TAGS[0]);
                } else {
                    fragmentTransaction.show(mShangWenFrag);
                }
                break;
            case 1:
                if (mShangZhaoFrag == null) {
                    mShangZhaoFrag = ShangZhaoFragment.newInstance();
                    fragmentTransaction.add(R.id.container, mShangZhaoFrag, FRAGMENT_TAGS[1]);
                } else {
                    fragmentTransaction.show(mShangZhaoFrag);
                }
                break;
            case 2:
                if (mShangJiFrag == null) {
                    mShangJiFrag = ShangJiFragment.newInstance();
                    fragmentTransaction.add(R.id.container, mShangJiFrag, FRAGMENT_TAGS[2]);
                } else {
                    fragmentTransaction.show(mShangJiFrag);
                }
                break;
            case 3:
                if (mXinZhiBaoFrag == null) {
                    mXinZhiBaoFrag = XinZhiBaoFragment.newInstance();
                    fragmentTransaction.add(R.id.container, mXinZhiBaoFrag, FRAGMENT_TAGS[3]);
                } else {
                    fragmentTransaction.show(mXinZhiBaoFrag);
                }
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
        //选中
        setTab(tv, getResources().getDrawable(TAB_IMG_SELECTED[i]));
        setTabTextColor(tv, R.color.tab_select_text_color);
    }

    //重置tab的状态（tab未选中状态）
    private void clearSelection(TextView tv) {
        //清除
        setTab(mTabShangWen, getResources().getDrawable(R.drawable.tab_shangwen));
        setTab(mTabShangZhao, getResources().getDrawable(R.drawable.tab_shangzhao));
        setTab(mTabShangJi, getResources().getDrawable(R.drawable.tab_shangji));
        setTab(mTabXinZhiBao, getResources().getDrawable(R.drawable.tab_xinzhibao));
        setTabTextColor(mTabShangWen, R.color.tab_unselect_text_color);
        setTabTextColor(mTabShangZhao, R.color.tab_unselect_text_color);
        setTabTextColor(mTabShangJi, R.color.tab_unselect_text_color);
        setTabTextColor(mTabXinZhiBao, R.color.tab_unselect_text_color);
    }

    private void setTabTextColor(TextView textView, int colorId) {
        int color = this.getResources().getColor(colorId);
        textView.setTextColor(color);
    }

    private void setTab(TextView tv, Drawable image) {
        tv.setCompoundDrawablePadding(8);
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());//非常重要，必须设置，否则图片不会显示
        tv.setCompoundDrawables(null, image, null, null);
    }

    //重置fragment状态
    private void hideFragments(FragmentTransaction transaction) {
        // TODO Auto-generated method stub
        if (mShangWenFrag != null) {
            transaction.hide(mShangWenFrag);
        }
        if (mShangZhaoFrag != null) {
            transaction.hide(mShangZhaoFrag);
        }
        if (mShangJiFrag != null) {
            transaction.hide(mShangJiFrag);
        }
        if (mXinZhiBaoFrag != null) {
            transaction.hide(mXinZhiBaoFrag);
        }
    }


    @Override
    public void openDrawer() {
        if (mDrawerLayout != null && !mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void closeDrawer() {

    }

    @Override
    public void enableScroll(boolean scrollable) {
        if (mDrawerLayout != null && !scrollable) {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }
}
