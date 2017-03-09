package com.jindou.myapplication.model.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by zhishi on 2017/3/9.
 * T :service类型
 * M :返回值类型json、String
 */

public class HttpCall<T> {

    private Call<T> call;

    public HttpCall(Class clazz, String methodName, Object... params) {
        this.call=getCall(clazz,methodName,params);
    }

    //1.
    private Call<T> getCall(Class clazz, String methodName, Object... params) {
        Object service = new HttpRequest().getService(clazz);
        Method[] methods = service.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (m.getName().equals(methodName)) {
                m.setAccessible(true);
                try {
                    return call = (Call<T>) m.invoke(service, params);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    //2.
    public void executeRequest(final HttpCallback callback) {

        if (call != null) {
            call.enqueue(new Callback<T>() {
                @Override
                public void onResponse(Call<T> call, Response<T> response) {
                    Timber.d("response="+response.body());
                    if (callback != null) {
                        callback.success(call, response);
                    }
                }

                @Override
                public void onFailure(Call<T> call, Throwable t) {
                    Timber.d("error message="+t.getMessage());
                    callback.failed(call, t);
                }
            });
        }

    }


}
