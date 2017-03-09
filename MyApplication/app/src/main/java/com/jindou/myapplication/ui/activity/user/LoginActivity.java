package com.jindou.myapplication.ui.activity.user;

import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.jindou.myapplication.R;
import com.jindou.myapplication.model.service.HttpCall;
import com.jindou.myapplication.model.service.HttpCallback;
import com.jindou.myapplication.model.service.UserService;
import com.jindou.myapplication.ui.activity.BaseTitleActivity;
import com.jindou.myapplication.ui.activity.MainActivity;
import com.jindou.myapplication.ui.util.ToastUtil;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

/**
 * A login screen that offers login via email/lock.
 */
public class LoginActivity extends BaseTitleActivity {

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
    public final static String KEY_TYPE = "type";

    public final static int LOGIN_SUCCESS = 0;
    public final static int LOGIN_FAILED = 1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOGIN_SUCCESS:
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    if (!isFinishing()) {
                        finish();
                    }
                    break;
                case LOGIN_FAILED:
                    break;
                default:
                    break;
            }
        }
    };

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

    @OnClick({R.id.userLogin, R.id.userRegister, R.id.iv_qq_weixin, R.id.iv_qq_login, R.id.iv_weibo_login, R.id.userForgetPwd, R.id.btShowPwd})
    public void otherLogin(View view) {
        switch (view.getId()) {
            case R.id.userLogin:
                String userAccount = etUserAccount.getText().toString();
                String userPassword = etUserPwd.getText().toString();
                if (TextUtils.isEmpty(userAccount)) {
                    ToastUtil.show(LoginActivity.this, getString(R.string.tip_account_or_phone_empty));
                    return;
                }
                if (TextUtils.isEmpty(userPassword)) {
                    ToastUtil.show(LoginActivity.this, getString(R.string.password_empty));
                    return;
                }
                login(userAccount, userPassword);
                break;
            case R.id.userRegister:
                Intent intent = new Intent(this, RegisterActivity.class);
                intent.putExtra(KEY_TYPE, "1");
                startActivity(intent);
                break;
            case R.id.iv_qq_weixin:
                ToastUtil.show(this, "weixin");
                break;
            case R.id.iv_qq_login:
                ToastUtil.show(this, "qq");
                break;
            case R.id.iv_weibo_login:
                ToastUtil.show(this, "weibo");
                break;
            case R.id.userForgetPwd:
                Intent intentRegister = new Intent(this, RegisterActivity.class);
                intentRegister.putExtra(KEY_TYPE, "2");
                startActivity(intentRegister);
                break;
            case R.id.btShowPwd:
                if (null == etUserPwd.getTransformationMethod()) {
                    etUserPwd.setTransformationMethod(new PasswordTransformationMethod());
                    btShowPwd.setImageResource(R.drawable.password_hide);
                } else {
                    etUserPwd.setTransformationMethod(null);
                    btShowPwd.setImageResource(R.drawable.password_show);
                }
                if (null != etUserPwd) {
                    String textUserPwd = etUserPwd.getText().toString();
                    if (!TextUtils.isEmpty(textUserPwd)) {
                        etUserPwd.setSelection(textUserPwd.length());
                    }
                }
                break;
            default:
                break;
        }
    }

    private void login(String account, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("param", account);
        map.put("password", password);
        HttpCall<String> login = new HttpCall<>(UserService.class, "login", map);
        login.executeRequest(new HttpCallback() {
            @Override
            public void success(Call call, Response response) {
                StringReader reader = new StringReader(response.body().toString().trim());
                JsonReader jsonReader = new JsonReader(reader);
                jsonReader.setLenient(true);
                JsonObject asJsonObject = new JsonParser().parse(jsonReader).getAsJsonObject();
                Timber.d("asJsonObject="+asJsonObject);
                int errcode = Integer.parseInt(asJsonObject.get("errcode").getAsString());
                Timber.d("errcode="+errcode);
                if (0==errcode) {
                    mHandler.sendEmptyMessage(LOGIN_SUCCESS);
                }else {
                    mHandler.sendEmptyMessage(LOGIN_FAILED);
                }
            }

            @Override
            public void failed(Call call, Throwable t) {

            }
        });
    }
}

