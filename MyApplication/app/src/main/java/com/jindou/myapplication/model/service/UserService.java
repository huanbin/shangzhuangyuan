package com.jindou.myapplication.model.service;

import com.google.gson.JsonObject;

import org.json.JSONObject;

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
    Call<String> login(@FieldMap Map<String, String> map);

    //    http://api.100szy.com/index.php?v=dev&c=user&a=logout
    @FormUrlEncoded
    @POST("index.php?v=dev&c=user&a=logout")
    Call<String> logout(@FieldMap Map<String, String> map);

    //    https://api.100szy.com/index.php?a=check_user&c=user&v=dev¶m=13012345678
    @GET("index.php")
    Call<String> checkIfExsits(@QueryMap Map<String, String> map);

    //    https://api.100szy.com/index.php?v=dev&c=user&a=forget
    @FormUrlEncoded
    @POST("index.php")
    Call<String> resetPassword(@QueryMap Map<String, String>queryMap,@FieldMap Map<String, String> map);


}
