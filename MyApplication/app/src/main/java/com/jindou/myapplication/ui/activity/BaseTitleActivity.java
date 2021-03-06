package com.jindou.myapplication.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.jindou.myapplication.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qiu.niorgai.StatusBarCompat;
import timber.log.Timber;

/**
 * Created by zhishi on 2017/2/21.
 */

public abstract class BaseTitleActivity extends AppCompatActivity {
    @BindView(R.id.common_title)
    public TextView tvTitle;
    @BindView(R.id.common_back)
    public ImageView ivBack;
    @BindView(R.id.common_complete)
    public TextView tvComplete;
    @BindView(R.id.common_overflow_menu)
    public ImageButton btOverflowMenu;

    public BaseTitleActivity mActivity;
    private FrameLayout mContainer;
    public abstract int getContentViewId();

    public abstract String setTitle();
    public  boolean setRightCompleteViewShow(){return false;};
    public  boolean setRightOverfloViewShow(){return false;};
//  点击导航栏右侧完成按键
//    public abstract void onClickComplete();
//    public abstract void onClickOverflow();
    public  void onClickComplete(){};
    public  void onClickOverflow(){};
    public  void handIntent(){};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_acticity_common_title);
//        if (Build.VERSION.SDK_INT==Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(getResources().getColor(R.color.bg_app_bar));
//        }
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.bg_app_bar));
        //android6.0默认状态栏浅色，必须设置高亮（否则在设置淡色状态栏颜色后，导致无法看见状态栏ui）
        if (Build.VERSION.SDK_INT==Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        mContainer= (FrameLayout) findViewById(R.id.layoutContainer);
        mContainer.addView(getLayoutInflater().inflate(getContentViewId(),mContainer,false));
        //注意上面动态添加的布局
        ButterKnife.bind(this);
        mActivity=this;
        handIntent();
        tvTitle.setText(setTitle());
        if (setRightCompleteViewShow()&&setRightOverfloViewShow()) {
            throw new RuntimeException("you can only set setRightCompleteViewShow or setRightOverfloViewShow one with value true...");
        }
        tvComplete.setVisibility(setRightCompleteViewShow()?View.VISIBLE:View.GONE);
        btOverflowMenu.setVisibility(setRightOverfloViewShow()?View.VISIBLE:View.GONE);
    }

    @OnClick({R.id.common_back,R.id.common_complete,R.id.common_overflow_menu})
    public void goBack(View view){
        switch (view.getId()) {
            case R.id.common_back:
                if (mActivity!=null&&!mActivity.isFinishing()) {
                    mActivity.finish();
                }
                break;
            case R.id.common_complete:
                onClickComplete();
                break;
            case R.id.common_overflow_menu:
                Timber.d("common_overflow_menu...");
                onClickOverflow();
                break;
            default:
                break;
        }

    }
    @Override
    public void onBackPressed() {
        Timber.d("you clicked mobile back key...");
        finish();
    }

}
