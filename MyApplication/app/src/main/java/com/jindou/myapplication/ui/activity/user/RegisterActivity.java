package com.jindou.myapplication.ui.activity.user;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.jindou.myapplication.R;
import com.jindou.myapplication.model.service.HttpCall;
import com.jindou.myapplication.model.service.HttpCallback;
import com.jindou.myapplication.model.service.UserService;
import com.jindou.myapplication.ui.activity.BaseTitleActivity;
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
 * Created by zhishi on 2017/2/16.
 */
public class RegisterActivity extends BaseTitleActivity {


    @BindView(R.id.userAccount)
    EditText userAccount;
    @BindView(R.id.userVerifyCode)
    EditText userVerifyCode;
    @BindView(R.id.btGetVerifyCode)
    Button btGetVerifyCode;
    @BindView(R.id.userPwd)
    EditText userPwd;
    @BindView(R.id.btShowPwd)
    ImageButton btShowPwd;
    @BindView(R.id.userRegister)
    Button userRegister;
    @BindView(R.id.userAlreadyRegister)
    Button userAlreadyRegister;
    private int type;
    private final int NOT_REGIST = 1;
    private final int HAS_REGIST = 2;
    private final int GET_VERIFYCODE_SUCCESS = 3;
    private final int GET_VERIFYCODE_FAILED = 4;
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case NOT_REGIST:
                    //没有注册，获取验证码
                    getVerifyCode(userAccount.getText().toString());
                    break;
                case HAS_REGIST:
                    break;
                case GET_VERIFYCODE_SUCCESS:
                    break;
                default:
                    break;
            }
        }
    };

    private void getVerifyCode(String account) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("operate", "reg");
        queryMap.put("value", account);
        Timber.d("queryMap=" + queryMap.toString());
        HttpCall<String> getVerifyCode = new HttpCall<>(UserService.class, "getVerifyCode", "reg", account);
        getVerifyCode.executeRequest(new HttpCallback() {
            @Override
            public void success(Call call, Response response) {
                Timber.d("response=" + response.toString());
                if (true) {
                    mHandler.sendEmptyMessage(GET_VERIFYCODE_SUCCESS);
                    Toast.makeText(RegisterActivity.this, "获取验证码...", Toast.LENGTH_SHORT).show();
                }else {
                    mHandler.sendEmptyMessage(GET_VERIFYCODE_FAILED);
                }
            }

            @Override
            public void failed(Call call, Throwable t) {
                Timber.d("error message=" + t.getMessage());
            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    public String setTitle() {
        if (type == 1) {
            return getString(R.string.register_account);
        } else if (type == 2) {

            return getString(R.string.forget_password);
        }
        return "";
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
    public void handIntent() {
        super.handIntent();
        type = Integer.parseInt(getIntent().getStringExtra(LoginActivity.KEY_TYPE));
    }

    /**
     * 以后会抽取
     *
     * @param view
     */
    @OnClick({R.id.common_back, R.id.btGetVerifyCode, R.id.btShowPwd, R.id.userRegister, R.id.userAlreadyRegister})
    public void clickBackNav(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                if (!isFinishing()) {
                    finish();
                }
                break;
            case R.id.btGetVerifyCode:
                String phoneOrEmailAccount=userAccount.getText().toString();
                if (TextUtils.isEmpty(phoneOrEmailAccount)) {
                    ToastUtil.show(this,"请输入手机号或邮箱");
                    return;
                }
                checkIfRegisty(phoneOrEmailAccount);
                break;
            case R.id.btShowPwd:
                Toast.makeText(RegisterActivity.this, "查看密码...", Toast.LENGTH_SHORT).show();
                if (userPwd.getTransformationMethod() == null) {
                    userPwd.setTransformationMethod(new PasswordTransformationMethod());
                    btShowPwd.setImageResource(R.drawable.password_hide);
                } else {
                    userPwd.setTransformationMethod(null);
                    btShowPwd.setImageResource(R.drawable.password_show);
                }
                String textUserPwd = userPwd.getText().toString();
                if (!TextUtils.isEmpty(textUserPwd)) {
                    userPwd.setSelection(textUserPwd.length());
                }
                break;
            case R.id.userRegister:
                String account = userAccount.getText().toString();
                String verifyCode = userVerifyCode.getText().toString();
                String password = userPwd.getText().toString();
                if (TextUtils.isEmpty(account)) {
                    ToastUtil.show(this, "请输入手机号或邮箱");
                    return;
                }
                if (TextUtils.isEmpty(verifyCode)) {
                    ToastUtil.show(this, "请输入验证码");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    ToastUtil.show(this, "请输入密码");
                    return;
                }

                registyAccount(account, verifyCode, password);

                break;
            case R.id.userAlreadyRegister:
                if (!isFinishing()) {
                    finish();
                }
                break;
            default:
                break;
        }

    }

    /**
     * 检查用户输入的手机号、邮箱是否已经注册
     * @param phoneOrEmailAccount
     */
    private void checkIfRegisty(String phoneOrEmailAccount) {
        //参数
        Map<String, String> queryStrings = new HashMap<String, String>();
        //v=dev&c=user&a=check_email_and_tel& param=13012345678
        queryStrings.put("v", "dev");
        queryStrings.put("c", "user");
        queryStrings.put("a", "check_email_and_tel");
        queryStrings.put("param", phoneOrEmailAccount);
        HttpCall call = new HttpCall(UserService.class, "checkIfRegisty", queryStrings);
        call.executeRequest(new HttpCallback() {
            @Override
            public void success(Call call, Response response) {
                Timber.d("response=" + response.body());
                if (true) {
                    mHandler.sendEmptyMessage(NOT_REGIST);
                }else {
                    mHandler.sendEmptyMessage(HAS_REGIST);
                }
            }

            @Override
            public void failed(Call call, Throwable t) {
            }
        });
    }

    /**
     * 注册账号
     *
     * @param account
     * @param verifyCode
     * @param password
     */
    private void registyAccount(String account, String verifyCode, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("code", verifyCode);
        map.put("param", account);
        map.put("password", password);
        HttpCall<String> registyAccount = new HttpCall<>(UserService.class, "registyAccount", map);
        registyAccount.executeRequest(new HttpCallback() {
            @Override
            public void success(Call call, Response response) {
                String result = response.body().toString();
//                Timber.d("result=" + result);
                StringReader reader = new StringReader(response.body().toString().trim());
                JsonReader jsonReader = new JsonReader(reader);
                jsonReader.setLenient(true);
                JsonObject asJsonObject = new JsonParser().parse(jsonReader).getAsJsonObject();
                int errcode = Integer.parseInt(asJsonObject.get("errcode").toString());
                if (0==errcode) {
                    ToastUtil.show(RegisterActivity.this,"注册成功");
                    if (!isFinishing()) {
                        finish();
                    }
                }else {
                    ToastUtil.show(RegisterActivity.this,"注册失败");
                }
            }

            @Override
            public void failed(Call call, Throwable t) {
                Timber.d("error message=" + t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        Timber.d("you clicked mobile back key...");
        finish();
    }
}
