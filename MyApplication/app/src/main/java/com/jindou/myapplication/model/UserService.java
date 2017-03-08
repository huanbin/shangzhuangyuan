package com.jindou.myapplication.model;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by zhishi on 2017/3/8.
 */

public interface UserService {
    ///v=dev&c=user&a=check_email_and_tel& param={account}
    //@QueryMap Map<String, String> options
    @GET("index.php")
    Call<String> getVerifyCode(@QueryMap Map<String, String> options);
}
