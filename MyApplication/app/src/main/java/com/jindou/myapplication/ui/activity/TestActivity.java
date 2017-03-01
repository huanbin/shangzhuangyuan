package com.jindou.myapplication.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jindou.myapplication.R;
import com.jindou.myapplication.utils.OkhttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        final TextView view = (TextView) findViewById(R.id.editText);

        String url="https://www.baidu.com";
        OkHttpClient okHttpClient=OkhttpUtils.getInstance(this);
        final Call call = okHttpClient.newCall(new Request.Builder()
                .url(url)
                .build()
        );
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String msg = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                            view.setText(msg);
                    }
                });
            }
        });
    }
}
