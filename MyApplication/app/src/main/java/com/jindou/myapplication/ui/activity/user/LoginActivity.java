package com.jindou.myapplication.ui.activity.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.jindou.myapplication.R;
import com.jindou.myapplication.ui.activity.BaseTitleActivity;
import com.jindou.myapplication.ui.util.ToastUtil;
import butterknife.BindView;
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
    @BindView(R.id.btShowPwd)
    public ImageButton btShowPwd;
//    @BindView(R.id.common_back)
//    public ImageView ivBackNav;
//    @BindView(R.id.common_title)
//    public TextView tvCommomTitle;

    public final static String KEY_TYPE="type";

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
    }

    /**
     * 以后会抽取
     * @param view
     */
//    @OnClick(R.id.common_back)
//    public void clickBackNav(View view){
//        if (!isFinishing()) {
//            finish();
//        }
//    }

//    @Override
//    public void onBackPressed() {
//        Timber.d("you clicked mobile back key...");
//        finish();
//    }
    @OnClick(R.id.btShowPwd)
    public void  onClick(View view){
        if (null==etUserPwd.getTransformationMethod()) {
            etUserPwd.setTransformationMethod(new PasswordTransformationMethod());
            btShowPwd.setImageResource(R.drawable.password_hide);
        }else {
            etUserPwd.setTransformationMethod(null);
            btShowPwd.setImageResource(R.drawable.password_show);
        }
        if (null!=etUserPwd) {
            String textUserPwd = etUserPwd.getText().toString();
            if (!TextUtils.isEmpty(textUserPwd)) {
                etUserPwd.setSelection(textUserPwd.length());
            }
        }
    }

    /**
     * 忘记密码
     * @param view
     */
    @OnClick(R.id.userForgetPwd)
    public void userForgetPwd(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra(KEY_TYPE,"2");
        startActivity(intent);
    }

    @OnClick({R.id.iv_qq_weixin,R.id.iv_qq_login,R.id.iv_weibo_login})
    public void otherLogin(View view){
        switch (view.getId()) {
            case R.id.iv_qq_weixin:
                ToastUtil.show(this,"weixin");
                break;
            case R.id.iv_qq_login:
                ToastUtil.show(this,"qq");
                break;
            case R.id.iv_weibo_login:
                ToastUtil.show(this,"weibo");
                break;
            default:
                break;
        }
    }
    @OnClick({R.id.userLogin,R.id.userRegister})
    public void clickLoginOrRegister(View view){
        Timber.d("you clicked button:"+view.getId());
        switch (view.getId()) {
            case R.id.userLogin:
                ToastUtil.show(this,"login...");
                break;
            case R.id.userRegister:
                /**
                 * 注册
                 */
                Intent intent = new Intent(this, RegisterActivity.class);
                intent.putExtra(KEY_TYPE,"1");
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}

