package com.jindou.myapplication.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jindou.myapplication.R;
import com.jindou.myapplication.ui.view.ShareDialog;
import com.jindou.myapplication.ui.view.SubDialog;
import com.jindou.myapplication.utils.IoUtils;
import com.jindou.myapplication.utils.OkhttpUtils;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Created by zhishi on 2017/3/6.
 */

public class AuthorArticleDetail extends BaseTitleActivity {

    @BindView(R.id.article_detail_title)
    TextView articleDetailTitle;
    @BindView(R.id.article_detail_avtar)
    ImageView articleDetailAvtar;
    @BindView(R.id.article_user_name)
    TextView articleUserName;
    @BindView(R.id.article_date)
    TextView articleDate;
    @BindView(R.id.article_time)
    TextView articleTime;
    @BindView(R.id.article_subscription)
    ImageButton articleSubscription;
    @BindView(R.id.article_webview)
    WebView articleWebview;
    @BindView(R.id.article_read_image)
    ImageView articleReadImage;
    @BindView(R.id.article_read_count)
    TextView articleReadCount;
    @BindView(R.id.article_share)
    ImageButton articleShare;
    @BindView(R.id.article_conllection)
    ImageButton articleConllection;

    private boolean isSubscription;
    private boolean isCollected;
    @Override
    public int getContentViewId() {
        return R.layout.activity_xinzhibao_article_detail;
    }

    @Override
    public String setTitle() {
        return "";
    }

    @Override
    public boolean setRightCompleteViewShow() {
        return false;
    }

    @Override
    public boolean setRightOverfloViewShow() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWebView();
        loadWebViewData();
    }

    @Override
    public void onClickOverflow() {
        super.onClickOverflow();
        SubDialog dialog = new SubDialog(this, Gravity.BOTTOM, R.style.ShareDialog);
        dialog.show();
    }

    @OnClick({R.id.article_subscription, R.id.article_share, R.id.article_conllection})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.article_subscription:
                isSubscription=!isSubscription;
                if (isSubscription) {
                    articleSubscription.setImageResource(R.drawable.subscription_already);
                }else {
                    articleSubscription.setImageResource(R.drawable.subscription);
                }
                break;
            case R.id.article_share:
                ShareDialog dialog = new ShareDialog(this, Gravity.BOTTOM, R.style.ShareDialog);
                dialog.show();
                break;
            case R.id.article_conllection:
                isCollected=!isCollected;
                if (isCollected) {
                    articleConllection.setImageResource(R.drawable.collected);
                }else {
                    articleConllection.setImageResource(R.drawable.nocollect);
                }
                break;
        }
    }

    private void loadWebViewData() {
        String finalHtmlContent = null;
        try {
            finalHtmlContent = IoUtils.readStringByInputstream(getAssets().open("articleDetail.html"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        articleWebview.loadDataWithBaseURL(null, finalHtmlContent, "text/html", "utf-8", null);
    }

    private void initWebView() {
        WebSettings settings = articleWebview.getSettings();
        settings.setDefaultTextEncodingName("UTF-8");//设置默认为utf-8
        settings.setJavaScriptEnabled(true);//启用js
        settings.setBlockNetworkImage(false);//允许网络数据加载
        settings.setLoadsImagesAutomatically(true);//自动加载图片
        //混合模式，比如：https网页内容内允许加载http图片资源
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        articleWebview.setHorizontalScrollBarEnabled(false);//关闭水平方向滚动条
        articleWebview.setWebViewClient(new MyWebViewClient());
    }

    //这里是java端调用webview的JS调整字体
    //android标准Large22sp Medium 18sp Small 14sp
    public void setActivityFontSize(int size) {
        if (articleWebview != null) {
            articleWebview.loadUrl(String.format("javascript:changeFontSize(" + size + ")"));
        }
    }

    class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            Timber.d("shouldOverrideUrlLoading..." + request.getUrl().toString());
            return true;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            Timber.d("shouldInterceptRequest..." + url);
            if (urlShouldBeHandledByWebView(url)) {
                return super.shouldInterceptRequest(view, request);
            }
            return handleRequestViaOkHttp(url);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            return super.shouldInterceptRequest(view, url);
        }

        private boolean urlShouldBeHandledByWebView(String url) {
            // file: Resolve requests to local files such as files from cache folder via WebView itself
            return url.startsWith("file:");
        }

        private WebResourceResponse handleRequestViaOkHttp(String url) {
//            OkHttpClient okHttpClient = new OkHttpClient();
            OkHttpClient okHttpClient = OkhttpUtils.getInstance(AuthorArticleDetail.this);
            /**
             * 注意这种方式只获取缓存数据，
             * Request request = new Request.Builder().cacheControl(new CacheControl.Builder().onlyIfCached().build()).url(url).build();
             * */
            final Call call = okHttpClient.newCall(new Request.Builder()
                    .url(url)
                    .build()
            );
//            Response response = null;
            final WebResourceResponse[] resourceResponse = {null};
            //response = call.execute();//会阻塞
            //异步方式
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Timber.e("error:" + e.getMessage());
                    Timber.e("获取网络图片失败，本地图片替代");
                    InputStream is = null;
                    try {
                        is = getAssets().open("wawa.jpg");
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    WebResourceResponse localResource = new WebResourceResponse("image/jpg", "UTF-8", is);
                    resourceResponse[0] = localResource;
                }

                @Override
                public void onResponse(Call call, Response response) {
                    resourceResponse[0] = new WebResourceResponse(
                            response.header("content-type", "image/*"), // You can set something other as default content-type
                            response.header("content-encoding", "utf-8"),  // Again, you can set another encoding as default
                            response.body().byteStream());
                }
            });
            return resourceResponse[0];
        }
    }


}

