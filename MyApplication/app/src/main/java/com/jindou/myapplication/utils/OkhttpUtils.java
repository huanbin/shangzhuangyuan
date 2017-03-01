package com.jindou.myapplication.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Created by zhishi on 2017/3/1.
 */

public class OkhttpUtils {
    private static OkHttpClient client;
    private static final long cacheSize = 1024*1024*20;//缓存文件最大限制大小20M
    private static OkHttpClient.Builder builder;

    static {
        builder = new OkHttpClient.Builder();
        builder.connectTimeout(8, TimeUnit.SECONDS);  //设置连接超时时间
        builder.writeTimeout(8, TimeUnit.SECONDS);//设置写入超时时间
        builder.readTimeout(8, TimeUnit.SECONDS);//设置读取数据超时时间
        builder.retryOnConnectionFailure(false);//设置不进行连接失败重试
        /**
         * 拦截器
         */
        /**
         * maxStale缓存过期时间（到达时间后,认为数据过期，会重新去网络下载数据，并不清理缓存）
         * maxAge缓存生存时间（到达指定时间后会清除缓存）
         */
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Timber.d("request="+request);
                Response response = chain.proceed(request);
                Timber.d("response="+response);

                String cacheControl = request.cacheControl().toString();
                if (TextUtils.isEmpty(cacheControl)) {
                    cacheControl = "public, max-age=60";
                }
                return response.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            }
        };
        builder.addNetworkInterceptor(interceptor);
    }

    public static OkHttpClient getInstance(Context context){
        String cachedirectory = context.getCacheDir().getAbsolutePath(); //设置缓存文件路径
        Cache cache = new Cache(new File(cachedirectory,"responses"), cacheSize);  //
        builder.cache(cache);//设置缓存方式
        client = builder.build();
        return client;
    }
}
