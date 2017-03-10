package com.jindou.myapplication.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.JsonObject;
import com.jindou.myapplication.R;
import com.jindou.myapplication.model.User;
import com.jindou.myapplication.model.service.HttpCall;
import com.jindou.myapplication.model.service.HttpCallback;
import com.jindou.myapplication.model.service.UserService;
import com.jindou.myapplication.ui.activity.BaseTitleActivity;
import com.jindou.myapplication.ui.activity.MainActivity;
import com.jindou.myapplication.ui.util.JsonUtil;
import com.jindou.myapplication.ui.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by zhishi on 2017/3/10.
 */

public class UserInfoActivity extends BaseTitleActivity {
    @BindView(R.id.accountIcon)
    ImageView mAccountIcon;
    @BindView(R.id.accountUploadIcon)
    ImageView mAccountUploadIcon;
    @BindView(R.id.accountName)
    TextView mAccountName;
    @BindView(R.id.modifyAccountName)
    TextView mModifyAccountName;
    @BindView(R.id.accountPhoneOrEmail)
    TextView mAccountPhoneOrEmail;
    @BindView(R.id.modifyAccountPhoneOrEmail)
    ImageView mModifyAccountPhoneOrEmail;
    @BindView(R.id.accountEmail)
    TextView mAccountEmail;
    @BindView(R.id.bindEmail)
    ImageView mBindEmail;
    @BindView(R.id.accountPassword)
    TextView mAccountPassword;
    @BindView(R.id.modifyAccountPassword)
    ImageView mModifyAccountPassword;
    @BindView(R.id.societyAccount)
    TextView mSocietyAccount;
    @BindView(R.id.modifySocietyAccount)
    ImageView mModifySocietyAccount;
    @BindView(R.id.accountLogout)
    Button mAccountLogout;

    private User mUser;
    //记录用户的登录方式（因为用户可以绑定手机号、邮箱）
    private boolean isUsePhoneLogin;
    private final int LOGOUT_SUCCESS=0;
    private final int LOGOUT_FAILED=1;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOGOUT_SUCCESS:
                    ToastUtil.show(UserInfoActivity.this,(String) msg.obj);
                    startActivity(new Intent(UserInfoActivity.this, MainActivity.class));
                    break;
                case LOGOUT_FAILED:
                    ToastUtil.show(UserInfoActivity.this,(String) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public int getContentViewId() {
        return R.layout.activity_account_manager;
    }

    @Override
    public String setTitle() {
        return getString(R.string.account_manager);
    }


    @Override
    public void handIntent() {
        super.handIntent();
        Intent intent = getIntent();
        mUser = (User) intent.getSerializableExtra(LoginActivity.USER_KEY);
        isUsePhoneLogin = TextUtils.isEmpty(mUser.getMobile()) ? false : true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mUser != null) {
            mAccountName.setText(mUser.getUsername());
            mAccountPhoneOrEmail.setText(TextUtils.isEmpty(mUser.getMobile()) ? getString(R.string.bind) : mUser.getMobile());
            mAccountEmail.setText(TextUtils.isEmpty(mUser.getEmail()) ? getString(R.string.bind) : mUser.getEmail());
            mAccountPassword.setText(mUser.getUsername());
            mSocietyAccount.setText(TextUtils.isEmpty(mUser.getSociety()) ? getString(R.string.no_society_account) : mUser.getSociety());
        }
    }

    @OnClick({R.id.accountUploadIcon, R.id.modifyAccountName, R.id.modifyAccountPhoneOrEmail, R.id.bindEmail, R.id.modifyAccountPassword, R.id.modifySocietyAccount, R.id.accountLogout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.accountUploadIcon:
                break;
            case R.id.modifyAccountName:
                break;
            case R.id.modifyAccountPhoneOrEmail:
                break;
            case R.id.bindEmail:
                break;
            case R.id.modifyAccountPassword:
                break;
            case R.id.modifySocietyAccount:
                break;
            case R.id.accountLogout:
                logout(mUser.getToken(), mUser.getUid());
                break;
            default:
                break;
        }
    }

    /**
     * 退出登录
     */
    private void logout(String token, String uid) {
        Map<String, String> mapBody = new HashMap<>();
        mapBody.put("token", token);
        mapBody.put("uid", uid);
        HttpCall<String> logout = new HttpCall<>(UserService.class, "logout", mapBody);
        logout.executeRequest(new HttpCallback() {
            @Override
            public void success(Call call, Response response) {
                JsonObject jsonObject= JsonUtil.getJsonObjectFromString(response.body().toString());
                Timber.d("jsonObject="+jsonObject);
                int errcode = Integer.parseInt(jsonObject.get("errcode").getAsString());
                String errorMessage = jsonObject.get("errstr").getAsString();
                if (0==errcode) {
                    mHandler.sendMessage(mHandler.obtainMessage(LOGOUT_SUCCESS,errorMessage));
                }else {
                    // TODO: 2017/3/10
                    mHandler.sendMessage(mHandler.obtainMessage(LOGOUT_FAILED,errorMessage));
                }
            }

            @Override
            public void failed(Call call, Throwable t) {

            }
        });
    }
}
