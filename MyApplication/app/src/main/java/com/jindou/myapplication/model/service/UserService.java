package com.jindou.myapplication.model.service;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by zhishi on 2017/3/8.
 */

public interface UserService {
    @GET("index.php")
    Call<String> checkIfRegisty(@QueryMap Map<String, String> options);

    @FormUrlEncoded
    @POST("index.php?a=send_auth_code&c=user&v=dev")
    Call<String> getVerifyCode(@Field("operate") String operate, @Field("value") String value);

    @FormUrlEncoded
    @POST("index.php?v=dev&c=user&a=register")
    Call<String> registyAccount(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("index.php?v=dev&c=user&a=login")
    Call<String> login(@FieldMap Map<String,String> map);
}
