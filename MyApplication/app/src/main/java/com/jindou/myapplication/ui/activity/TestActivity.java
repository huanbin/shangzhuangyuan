package com.jindou.myapplication.ui.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jindou.myapplication.R;
import com.jindou.myapplication.utils.OkhttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestActivity extends AppCompatActivity {


    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_picture_dialog_view);
//        List<String> urls=new ArrayList<>();
//        urls.add("http://pic.58pic.com/58pic/16/62/63/97m58PICyWM_1024.jpg");
//        urls.add("http://img.taopic.com/uploads/allimg/120423/107913-12042323220753.jpg");
//        urls.add("http://pic35.nipic.com/20131121/2531170_145358633000_2.jpg");
//        viewPager = (ViewPager) findViewById(R.id.viewPagerScanPicture);
//        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager(),this,urls));
    }

//    public class MyAdapter extends FragmentPagerAdapter{
//
//        private List<String> urls;
//        private Context context;

//        public MyAdapter(FragmentManager fm,Context context,List<String> urls) {
//            super(fm);
//            this.urls=urls;
//            this.context=context;
//        }
//
//
//        @Override
//        public Fragment getItem(int position) {
//            return ImageFragment.newInstance(context,urls.get(position));
//        }
//
//        @Override
//        public int getCount() {
//            return urls.size();
//        }
//    }

    /**
     * okhttp 缓存
     */
   void test(){
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
