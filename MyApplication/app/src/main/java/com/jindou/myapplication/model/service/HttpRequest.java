package com.jindou.myapplication.model.service;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by zhishi on 2017/3/9.
 */

public class HttpRequest {

    private static final Retrofit retrofit;

    static {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.100szy.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    public <T> T getService(Class<T> clazz){
        return retrofit.create(clazz);
    }

}
