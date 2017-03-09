package com.jindou.myapplication.model.service;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by zhishi on 2017/3/9.
 */

public interface HttpCallback<T>{
     void success(Call<T> call, Response<T> response);
     void failed(Call<T> call, Throwable t);
}
