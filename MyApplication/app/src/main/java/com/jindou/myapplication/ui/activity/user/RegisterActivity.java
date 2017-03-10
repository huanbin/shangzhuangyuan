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
import com.jindou.myapplication.ui.util.JsonUtil;
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
    private final int GET_VERIFYCODE_AFTER60 = 5;
    private final int ACCOUNT_EXSIT= 6;
    private final int ACCOUNT_NOT_EXSIT = 7;
    private final int PASSWORD_RESET_OK = 8;

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
                    ToastUtil.show(RegisterActivity.this,(String) msg.obj);
                    break;
                case GET_VERIFYCODE_FAILED:
                    //出错（手机号或邮箱已经注册过）
                    ToastUtil.show(RegisterActivity.this,(String) msg.obj);
                    break;
                case GET_VERIFYCODE_SUCCESS:
                    ToastUtil.show(RegisterActivity.this,"验证码已发送");
                    break;
                case ACCOUNT_EXSIT:
                    //账号存在(重置密码时检查账号)
                    Timber.d("用户存在，发送验证码");
                    getVerifyCode(userAccount.getText().toString().trim());
                    break;
                case ACCOUNT_NOT_EXSIT:
                    ToastUtil.show(RegisterActivity.this,(String)msg.obj);
                    btGetVerifyCode.setText(R.string.get_verifycode);
                    break;
                case PASSWORD_RESET_OK:
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
                JsonObject jsonObject = JsonUtil.getJsonObjectFromString(response.body().toString());
                String errcode=jsonObject.get("errcode").getAsString();
                String errstr=jsonObject.get("errstr").getAsString();
                if ("0".equals(errcode)) {
                    mHandler.sendEmptyMessage(GET_VERIFYCODE_SUCCESS);
//                    Toast.makeText(RegisterActivity.this, "获取验证码...", Toast.LENGTH_SHORT).show();
                }else {
                    mHandler.sendMessage(mHandler.obtainMessage(GET_VERIFYCODE_FAILED,errstr));
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
        if (type == LoginActivity.REGIST) {
            userRegister.setText(R.string.register);
            return getString(R.string.register_account);
        } else if (type == LoginActivity.FORGET_PASSWORD) {
            userRegister.setText(R.string.complete);
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
        type = getIntent().getIntExtra(LoginActivity.KEY_TYPE,0);
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
                String phoneOrEmailAccount=userAccount.getText().toString().trim();
                if (TextUtils.isEmpty(phoneOrEmailAccount)) {
                    ToastUtil.show(this,"请输入手机号或邮箱");
                    return;
                }
                btGetVerifyCode.setText("60s后重新发送");
                if (LoginActivity.REGIST==type) {
                    //注册页面先检查账户是否已经注册
                    checkIfRegisty(phoneOrEmailAccount);
                } else if (LoginActivity.FORGET_PASSWORD==type) {
                    //重置密码页面，验证用否是否存在，存在则获取验证码
                    Timber.d("忘记密码，验证用户是否存在");
                    checkAccountIfExsits(phoneOrEmailAccount);
                }
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
                String account = userAccount.getText().toString().trim();
                String verifyCode = userVerifyCode.getText().toString().trim();
                String password = userPwd.getText().toString().trim();
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
                if (password.length()>20||password.length()<6) {
                    ToastUtil.show(this, "请输入6到20位密码");
                }
                if (LoginActivity.FORGET_PASSWORD==type) {
                    resetPassword(verifyCode,account,password);
                } else if (LoginActivity.REGIST==type) {
                    registyAccount(account, verifyCode, password);
                }

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
     * 重置密码
     */
    private void resetPassword(String code, String account, final String password) {
        Map<String,String> queryMap=new HashMap<>();
        queryMap.put("a","forget");
        queryMap.put("c","user");
        queryMap.put("v","dev");
        Map<String,String> bodyMap=new HashMap<>();
        bodyMap.put("code",code);
        bodyMap.put("param",account);
        bodyMap.put("password",password);
        final HttpCall<String> resetPassword = new HttpCall<>(UserService.class, "resetPassword", queryMap, bodyMap);
        resetPassword.executeRequest(new HttpCallback() {
            @Override
            public void success(Call call, Response response) {
                JsonObject jsonObject = JsonUtil.getJsonObjectFromString(response.body().toString());
                String errcode=jsonObject.get("errcode").getAsString();
                String errstr=jsonObject.get("errstr").getAsString();
                if ("0".equals(errcode)) {
                    mHandler.sendMessage(mHandler.obtainMessage(PASSWORD_RESET_OK,errstr));
                    ToastUtil.show(RegisterActivity.this,"密码修改成功");
                    userAccount.setText("");
                    userVerifyCode.setText("");
                    userPwd.setText("");
                }else {
                    ToastUtil.show(RegisterActivity.this,errstr);
                }
            }

            @Override
            public void failed(Call call, Throwable t) {

            }
        });


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
                JsonObject jsonObject = JsonUtil.getJsonObjectFromString(response.body().toString());
                String errcode=jsonObject.get("errcode").getAsString();
                String errstr=jsonObject.get("errstr").getAsString();
                if ("0".equals(errcode)) {
                    mHandler.sendEmptyMessage(NOT_REGIST);
                }else {
                    mHandler.sendMessage(mHandler.obtainMessage(HAS_REGIST,errstr));
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
//                Timber.d("result=" + result);
                JsonObject asJsonObject = JsonUtil.getJsonObjectFromString(response.body().toString());
                String errcode =asJsonObject.get("errcode").getAsString();
                String errstr =asJsonObject.get("errstr").getAsString();
                if ("0".equals(errcode)) {
                    ToastUtil.show(RegisterActivity.this,"注册成功");
                    if (!isFinishing()) {
                        finish();
                    }
                }else {
                    ToastUtil.show(RegisterActivity.this,errstr);
                }
            }

            @Override
            public void failed(Call call, Throwable t) {
                Timber.d("error message=" + t.getMessage());
            }
        });
    }
//    a=check_user&c=user&v=dev&m=13012345678"
    private void checkAccountIfExsits(String account){
        Map<String,String> queryMap=new HashMap<>();
        queryMap.put("a","check_user");
        queryMap.put("c","user");
        queryMap.put("v","dev");
        queryMap.put("param",account);
        HttpCall<String> checkIfExsits = new HttpCall<>(UserService.class, "checkIfExsits", queryMap);
        checkIfExsits.executeRequest(new HttpCallback() {
            @Override
            public void success(Call call, Response response) {
                JsonObject jsonObject=JsonUtil.getJsonObjectFromString(response.body().toString());
                String errcode=jsonObject.get("errcode").getAsString();
                String errstr=jsonObject.get("errstr").getAsString();
                if ("0".equals(errcode)) {
                    mHandler.sendMessage(mHandler.obtainMessage(ACCOUNT_EXSIT,errstr));
                }else {
                    mHandler.sendMessage(mHandler.obtainMessage(ACCOUNT_NOT_EXSIT,errstr));
                }
            }

            @Override
            public void failed(Call call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Timber.d("you clicked mobile back key...");
        finish();
    }
}
