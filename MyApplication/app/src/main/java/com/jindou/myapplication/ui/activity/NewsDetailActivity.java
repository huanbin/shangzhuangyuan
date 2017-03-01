package com.jindou.myapplication.ui.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jindou.myapplication.R;
import com.jindou.myapplication.model.ImageModel;
import com.jindou.myapplication.ui.fragment.FullScreenDialogFragment;
import com.jindou.myapplication.ui.view.OverflowMenuDialog;
import com.jindou.myapplication.ui.view.ShareDialog;
import com.jindou.myapplication.utils.IoUtils;
import com.jindou.myapplication.utils.OkhttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Created by zhishi on 2017/2/23.
 */
public class NewsDetailActivity extends BaseTitleActivity implements OverflowMenuDialog.ICollectedListener {

    @BindView(R.id.webView)
    public WebView mWebView;
    @BindView(R.id.tvReadCount)
    public TextView tvReadCount;
    @BindView(R.id.ibCollect)
    public ImageButton ibCollect;
    @BindView(R.id.ibShare)
    public ImageButton ibShare;

    private static boolean isColleted;

    @Override
    public int getContentViewId() {
        return R.layout.activity_news_detail;
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
    public void onClickComplete() {
    }

    @Override
    public void onClickOverflow() {
        OverflowMenuDialog dialog = new OverflowMenuDialog(NewsDetailActivity.this, Gravity.BOTTOM, R.style.ShareDialog);
        dialog.show();
    }

    @OnClick({R.id.ibCollect, R.id.ibShare})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibCollect:
                if (!isColleted) {
                    isColleted = true;
                    ibCollect.setImageResource(R.drawable.collected);
                } else {
                    isColleted = false;
                    ibCollect.setImageResource(R.drawable.nocollect);
                }
                break;
            case R.id.ibShare:
                ShareDialog dialog = new ShareDialog(NewsDetailActivity.this, Gravity.BOTTOM, R.style.ShareDialog);
                dialog.show();
                break;
            default:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWebView();
        loadWebViewData();
    }

    private void loadWebViewData() {
        try {
            String data = IoUtils.readStringByInputstream(getAssets().open("format.html"));
            String jsonSource = IoUtils.readStringByInputstream(getAssets().open("ArticleDetail.json"));
            JSONObject jsonObject = new JSONObject(jsonSource);
            JSONObject jsonData = jsonObject.optJSONObject("AQ4RPLHG00964LQ9");
            String title = jsonData.optString("title");
            String source = jsonData.optString("source");
            String time = jsonData.optString("ptime");
            String content = jsonData.optString("body");
            String aLink = jsonData.optString("link");
            String shareLink = jsonData.optString("shareLink");
            Timber.d("data=" + data);
            String finalHtmlContent = String.format(data, title, source, time, content, shareLink);

            JSONArray imgs = jsonData.optJSONArray("img");
            Gson gson = new Gson();
            List<ImageModel> imageModelList = gson.fromJson(imgs.toString(), new TypeToken<List<ImageModel>>() {
            }.getType());
            //js调用java方法
            mWebView.addJavascriptInterface(new JSInvokeClass(imageModelList), "jsToast");
            for (ImageModel model : imageModelList) {
                String pic = model.getSrc();
                //拼写html和js时一定注意字符串单双引号，尤其js调用参数（onclick）
                String imageHtml = "<img width=\"%spx\" height=\"%spx\" alt=\"%s\" src=\"%s\" onclick=\"jsToast.scanPictures('" + model.getSrc() + "');\"   />";
                String[] pixel = model.getPixel().split("\\*");
                String finalImageHtml = String.format(imageHtml, pixel[0], pixel[1], model.getAlt(), model.getSrc());
                Timber.d("finalImageHtml=" + finalImageHtml);
                String ex = "<!--IMG#\\d+-->";
                Pattern p = Pattern.compile(ex);
                Matcher matcher = p.matcher(finalHtmlContent);
                finalHtmlContent = matcher.replaceFirst(finalImageHtml);
            }
//            Timber.d("finalHtmlContent="+finalHtmlContent);
            mWebView.loadDataWithBaseURL(null, finalHtmlContent, "text/html", "utf-8", null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setDefaultTextEncodingName("UTF-8");//设置默认为utf-8
        settings.setJavaScriptEnabled(true);//启用js
        settings.setBlockNetworkImage(false);//允许网络数据加载
        settings.setLoadsImagesAutomatically(true);//自动加载图片
        //混合模式，比如：https网页内容内允许加载http图片资源
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.setHorizontalScrollBarEnabled(false);//关闭水平方向滚动条
        mWebView.setWebViewClient(new MyWebViewClient());
    }


    /**
     * 通知OverflowMenuDialog已经收藏
     *
     * @return
     */
    @Override
    public boolean setDialogCollected() {
        return isColleted;
    }

    public final class JSInvokeClass {
        private List<ImageModel> images;

        public JSInvokeClass(List<ImageModel> images) {
            this.images = images;
        }

        //注意android4.4以上，js调用的方法，必须加上注解@JavascriptInterface，否则无法调用
        @JavascriptInterface
        public void scanPictures(String msg) {
//            Toast.makeText(NewsDetailActivity.this, "通过js调用的Java方法:" + msg, Toast.LENGTH_SHORT).show();
            FullScreenDialogFragment.newInstance(NewsDetailActivity.this, images, 2).show(getSupportFragmentManager(), FullScreenDialogFragment.TAG_NAME);
        }
    }

    private class MyWebViewClient extends WebViewClient {

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
            OkHttpClient okHttpClient= OkhttpUtils.getInstance(NewsDetailActivity.this);
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

    /**
     * 通知NewsDetailActivity已经收藏
     *
     * @param pColleted
     */
    public void setActivityCollected(boolean pColleted) {
        isColleted = pColleted;
        if (isColleted) {
            ibCollect.setImageResource(R.drawable.collected);
        } else {
            ibCollect.setImageResource(R.drawable.nocollect);
        }
    }

    //这里是java端调用webview的JS调整字体
    //android标准Large22sp Medium 18sp Small 14sp
    public void setActivityFontSize(int size) {
        if (mWebView != null) {
            mWebView.loadUrl(String.format("javascript:changeFontSize(" + size + ")"));
        }
    }
}
