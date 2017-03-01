package com.jindou.myapplication.ui.activity.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;
import com.jindou.myapplication.R;
import com.jindou.myapplication.ui.activity.BaseTitleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * A login screen that offers login via email/lock.
 */
public class LoginActivity extends BaseTitleActivity{

    @BindView(R.id.userAccount)
    public EditText etUserAccount;
    @BindView(R.id.userPwd)
    public EditText etUserPwd;
    @BindView(R.id.userForgetPwd)
    public TextView tvUserForgetPwd;
    @BindView(R.id.userLogin)
    public Button btUserLogin;
    @BindView(R.id.userRegister)
    public Button btUserRegister;
    @BindView(R.id.common_back)
    public ImageView ivBackNav;
    @BindView(R.id.common_title)
    public TextView tvCommomTitle;

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    public String setTitle() {
        return getResources().getString(R.string.login);
    }

    @Override
    public boolean setRightCompleteViewShow() {
        return false;
    }

    @Override
    public boolean setRightOverfloViewShow() {
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.bg_app_bar),true);
//        ButterKnife.bind(this);
//        tvCommomTitle.setText(R.string.login);
    }

    /**
     * 以后会抽取
     * @param view
     */
    @OnClick(R.id.common_back)
    public void clickBackNav(View view){
        if (!isFinishing()) {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Timber.d("you clicked mobile back key...");
        finish();
    }

    @OnClick({R.id.userLogin,R.id.userRegister})
    public void clickLoginOrRegister(View view){
        Timber.d("you clicked button:"+view.getId());
        switch (view.getId()) {
            case R.id.userLogin:
                break;
            case R.id.userRegister:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            default:
                break;
        }
    }

    //    @OnTouch(R.id.userPwd)
//    public boolean showOrHidePwd(View v, MotionEvent event){
//        // et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
//        EditText editText=(EditText)v;
//        Drawable drawable = editText.getCompoundDrawables()[2];
//        //如果右边没有图片，不再处理
//        if (drawable == null)
//            return false;
//        //如果不是按下事件，不再处理
//        if (event.getAction() != MotionEvent.ACTION_UP)
//            return false;
//        if (event.getX() > editText.getWidth()
//                - editText.getPaddingRight()
//                - drawable.getIntrinsicWidth()){
//            Timber.d("you clicked password");
//            editText.setTransformationMethod(new PasswordTransformationMethod());
//        }
//        return false;
//    }

}

