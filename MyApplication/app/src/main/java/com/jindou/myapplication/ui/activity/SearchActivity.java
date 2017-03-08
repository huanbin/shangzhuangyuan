package com.jindou.myapplication.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andview.refreshview.XRefreshView;
import com.jindou.myapplication.R;
import com.jindou.myapplication.ui.adapter.SearchAdapter;
import com.jindou.myapplication.ui.fragment.ShangWenFragment;
import com.jindou.myapplication.ui.util.ToastUtil;
import com.jindou.myapplication.ui.util.XRefreshViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qiu.niorgai.StatusBarCompat;

/**
 * Created by zhishi on 2017/3/8.
 */

public class SearchActivity extends AppCompatActivity implements OnClickListener {

    @BindView(R.id.search_back)
    ImageButton searchBack;
    @BindView(R.id.search_view)
    EditText searchView;
    @BindView(R.id.search_cancle)
    TextView searchCancle;
    //动态view(Butterknife无法注解？)
    private FrameLayout viewContainer;
    private XRefreshView xRefreshView;
    private RecyclerView recyclerView;
    private LinearLayout searchHuizhan;
    private LinearLayout searchData;
    private LinearLayout searchZhaoShang;
    private LinearLayout searchZhaoBiao;
    private LinearLayout searchReport;
    private ImageView searchHuizhanImg;
    private ImageView searchDataImg;
    private ImageView searchZhaoShangImg;
    private ImageView searchZhaoBiaoImg;
    private ImageView searchReportImg;
    private TextView searchHuizhanText;
    private TextView searchDataText;
    private TextView searchZhaoShangText;
    private TextView searchZhaoBiaoText;
    private TextView searchReportText;

    private String queryTextHint;
    //搜索页面类型
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        handleIntent();
        loadView();
        ButterKnife.bind(this);
        initView();
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#FFF7F7F7"));
        //android6.0默认状态栏浅色，必须设置高亮（否则在设置淡色状态栏颜色后，导致无法看见状态栏ui）
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    private void initView() {
        searchView.setHint(queryTextHint);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 12) {
                    ToastUtil.show(SearchActivity.this, "您输入的关键词太长了~");
                }
            }
        });

        searchView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }
        });

        searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Toast.makeText(SearchActivity.this, "1111111", Toast.LENGTH_SHORT).show();
                    // TODO: 2017/3/8  
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 动态显示商闻和商招的搜索页面
     */
    private void loadView() {
        FrameLayout container = (FrameLayout) findViewById(R.id.viewContainer);
        if (type == 1) {
            View view = LayoutInflater.from(this).inflate(R.layout.common_recycle_view, container, false);
            container.removeAllViews();
            container.addView(view);
            //动态View 无法注解
            xRefreshView = (XRefreshView) findViewById(R.id.authorArticleRefreshView);
            recyclerView = (RecyclerView) findViewById(R.id.authorArticleRecyclerView);
            List<Object> modelDatas = new ArrayList<Object>();
            XRefreshViewUtils.initViewsAnDatas(this, xRefreshView, recyclerView, new SearchAdapter(modelDatas, this), modelDatas);
        } else {
            if (type == 2) {
                View view = LayoutInflater.from(this).inflate(R.layout.search_shangzhao_view, container, false);
                container.removeAllViews();
                container.addView(view);
                searchHuizhan = (LinearLayout) findViewById(R.id.searchHuiZhan);
                searchHuizhan.setOnClickListener(this);
                searchData = (LinearLayout) findViewById(R.id.searchData);
                searchData.setOnClickListener(this);
                searchZhaoShang = (LinearLayout) findViewById(R.id.searchZhaoShang);
                searchZhaoShang.setOnClickListener(this);
                searchZhaoBiao = (LinearLayout) findViewById(R.id.searchZhaoBiao);
                searchZhaoBiao.setOnClickListener(this);
                searchReport = (LinearLayout) findViewById(R.id.searchReport);
                searchReport.setOnClickListener(this);
                searchHuizhanImg = (ImageView) findViewById(R.id.searchHuiZhanImg);
                searchDataImg = (ImageView) findViewById(R.id.searchDataImg);
                searchZhaoShangImg = (ImageView) findViewById(R.id.searchZhaoShangImg);
                searchZhaoBiaoImg = (ImageView) findViewById(R.id.searchZhaoBiaoImg);
                searchReportImg = (ImageView) findViewById(R.id.searchReportImg);
                searchHuizhanText = (TextView) findViewById(R.id.searchHuiZhanText);
                searchDataText = (TextView) findViewById(R.id.searchDataText);
                searchZhaoShangText = (TextView) findViewById(R.id.searchZhaoShangText);
                searchZhaoBiaoText = (TextView) findViewById(R.id.searchZhaoBiaoText);
                searchReportText = (TextView) findViewById(R.id.searchReportText);
                setCurrent(0);
            }
        }
    }

    private void handleIntent() {
        Intent intent = getIntent();
        queryTextHint = intent.getStringExtra(ShangWenFragment.SEARCH_SOURCE);
        type = intent.getIntExtra(ShangWenFragment.SEARCH_TYPE, 0);
    }

    @OnClick({R.id.search_back, R.id.search_view, R.id.search_cancle})
    public void onClickTitle(View view) {
        switch (view.getId()) {
            case R.id.search_back:
                if (!isFinishing()) {
                    finish();
                }
                break;
            case R.id.search_view:

                break;
            case R.id.search_cancle:
                searchView.setText("");
                break;
        }
    }

    public void setCurrent(int current) {
        switch (current) {
            case 0:
                searchHuizhanImg.setImageResource(R.drawable.search_huizhan_selected);
                searchHuizhanText.setTextColor(Color.parseColor("#FFF09E0A"));
                break;
            case 1:
                searchDataImg.setImageResource(R.drawable.search_data_selected);
                searchDataText.setTextColor(Color.parseColor("#FFF09E0A"));

                break;
            case 2:
                searchZhaoShangImg.setImageResource(R.drawable.search_zhaoshang_selected);
                searchZhaoShangText.setTextColor(Color.parseColor("#FFF09E0A"));

                break;
            case 3:
                searchZhaoBiaoImg.setImageResource(R.drawable.search_zhaobiao_selected);
                searchZhaoBiaoText.setTextColor(Color.parseColor("#FFF09E0A"));

                break;
            case 4:
                searchReportImg.setImageResource(R.drawable.search_report_selected);
                searchReportText.setTextColor(Color.parseColor("#FFF09E0A"));
                break;
            default:
                break;
        }

    }

    private void clearAll() {
        searchHuizhanImg.setImageResource(R.drawable.search_huizhan);
        searchDataImg.setImageResource(R.drawable.search_data);
        searchZhaoShangImg.setImageResource(R.drawable.search_zhaoshang);
        searchZhaoBiaoImg.setImageResource(R.drawable.search_zhaobiao);
        searchReportImg.setImageResource(R.drawable.search_report);
        searchHuizhanText.setTextColor(Color.parseColor("#FF333333"));
        searchDataText.setTextColor(Color.parseColor("#FF333333"));
        searchZhaoShangText.setTextColor(Color.parseColor("#FF333333"));
        searchZhaoBiaoText.setTextColor(Color.parseColor("#FF333333"));
        searchReportText.setTextColor(Color.parseColor("#FF333333"));
    }

    @Override
    public void onClick(View v) {
        clearAll();
        switch (v.getId()) {
            case R.id.searchHuiZhan:
                setCurrent(0);
                break;
            case R.id.searchData:
                setCurrent(1);
                break;
            case R.id.searchZhaoShang:
                setCurrent(2);
                break;
            case R.id.searchZhaoBiao:
                setCurrent(3);
                break;
            case R.id.searchReport:
                setCurrent(4);
                break;
            default:
                break;
        }
    }
}
