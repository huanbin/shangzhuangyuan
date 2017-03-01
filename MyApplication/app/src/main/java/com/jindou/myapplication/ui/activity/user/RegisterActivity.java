package com.jindou.myapplication.ui.activity.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.jindou.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by zhishi on 2017/2/16.
 */
public class RegisterActivity extends AppCompatActivity{
    @BindView(R.id.common_title)
    public TextView tvCommomTitle;
    @BindView(R.id.btGetVerifyCode)
//    public Button btGetVerifyCode;
    public TextView btGetVerifyCode;
    @BindView(R.id.btShowPwd)
    public ImageButton mIbtShowPwd;
    @BindView(R.id.userPwd)
    public EditText etUserPwd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.bg_app_bar),true);
        ButterKnife.bind(this);
        tvCommomTitle.setText(R.string.register_account);
    }
    /**
     * 以后会抽取
     * @param view
     */
    @OnClick({R.id.common_back,R.id.btGetVerifyCode,R.id.btShowPwd})
    public void clickBackNav(View view){
        switch (view.getId()) {
            case R.id.common_back:
                if (!isFinishing()) {
                    finish();
                }
                break;
            case R.id.btGetVerifyCode:
                Toast.makeText(RegisterActivity.this,"获取验证码...",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btShowPwd:
                Toast.makeText(RegisterActivity.this,"查看密码...",Toast.LENGTH_SHORT).show();
                if (etUserPwd.getTransformationMethod()==null) {
                    etUserPwd.setTransformationMethod(new PasswordTransformationMethod());
                    mIbtShowPwd.setImageResource(R.drawable.password_hide);
                }else {
                    etUserPwd.setTransformationMethod(null);
                    mIbtShowPwd.setImageResource(R.drawable.password_show);
                }
                String textUserPwd = etUserPwd.getText().toString();
                if (!TextUtils.isEmpty(textUserPwd)) {
                    etUserPwd.setSelection(textUserPwd.length());
                }
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
