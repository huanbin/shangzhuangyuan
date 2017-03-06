package com.jindou.myapplication.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.jindou.myapplication.R;
import com.jindou.myapplication.ui.adapter.AuthorArticleAdapter;
import com.jindou.myapplication.model.XinzhibaoNewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import qiu.niorgai.StatusBarCompat;

/**
 * Created by zhishi on 2017/3/6.
 */
public class AuthorActivity extends AppCompatActivity {

    @BindView(R.id.authorArticleRecyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.authorArticleRefreshView)
    public XRefreshView xRefreshView;
    private AuthorArticleAdapter recyclerviewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<XinzhibaoNewModel> xinzhibaoNewModelList;

//    @Override
//    public int getContentViewId() {
//        return R.layout.activity_author_center;
//    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(getResources().getColor(R.color.transparent));

        setContentView(R.layout.activity_author_center);
        ButterKnife.bind(this);
        initViewsAnDatas();
    }

    private void initViewsAnDatas() {
        xinzhibaoNewModelList = new ArrayList<>();
        //if true，data change can not change  recyclerView size
        recyclerView.setHasFixedSize(true);
//        recyclerView.addOnItemTouchListener();
        recyclerviewAdapter = new AuthorArticleAdapter(xinzhibaoNewModelList, AuthorActivity.this);
        layoutManager = new LinearLayoutManager(AuthorActivity.this);
        recyclerView.setLayoutManager(layoutManager);
//        动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        分割线
//        recyclerView.addItemDecoration(new DividerItemDecoration(AuthorActivity.this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(recyclerviewAdapter);
//        加载更多
        recyclerviewAdapter.setCustomLoadMoreView(new XRefreshViewFooter(AuthorActivity.this));
//
        configXRfreshView(xRefreshView, new XRefreshView.SimpleXRefreshListener() {
            //          下拉刷新
            @Override
            public void onRefresh() {
                super.onRefresh();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xRefreshView.stopRefresh();
                    }
                }, 500);
            }

            //            上拉加载更多
            @Override
            public void onLoadMore(boolean isSilence) {
                super.onLoadMore(isSilence);

                loadMoreData();

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        // 刷新完成必须调用此方法停止加载
                        xRefreshView.stopLoadMore();
                    }
                }, 1000);
            }
        });
//      为空View添加点击事件
        setEmptyViewClickListener(xRefreshView, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestRecyclerViewData();
            }
        });
//      请求数据
        requestRecyclerViewData();
    }

    private void setEmptyViewClickListener(XRefreshView xRefreshView, View.OnClickListener listener) {
        View emptyView = xRefreshView.getEmptyView();
        if (emptyView != null) {
            emptyView.setOnClickListener(listener);
        }
    }

    private void configXRfreshView(XRefreshView xRefreshView, XRefreshView.SimpleXRefreshListener listener) {
        xRefreshView.setPullLoadEnable(true);
        //设置刷新完成以后，headerview固定的时间
        xRefreshView.setPinnedTime(1000);
        xRefreshView.setPullLoadEnable(true);
        xRefreshView.setMoveForHorizontal(true);
        xRefreshView.setAutoLoadMore(true);
        //两种方式设置空布局，传入空布局的view或者传入布局id都可以
//        TextView textView = new TextView(this);
//        textView.setText("没有数据，点击刷新");
//        textView.setGravity(Gravity.CENTER);
//        xRefreshView.setEmptyView(textView);
        xRefreshView.setEmptyView(R.layout.layout_emptyview);
        xRefreshView.setXRefreshViewListener(listener);
    }

    //  初始化RecyclerView数据
    private void requestRecyclerViewData() {
        xinzhibaoNewModelList.clear();
        for (int i = 0; i < 6; i++) {
            XinzhibaoNewModel news = new XinzhibaoNewModel("imageUrl", "商状元", "2017-01-06 11:46", false, "contentImageUrl", "小固然：原油沥青日内操作原理与建议原理与建议", 200, 2);
            xinzhibaoNewModelList.add(news);
        }
        recyclerviewAdapter.notifyDataSetChanged();
    }

    //    上拉加载更多数据
    private void loadMoreData() {
        for (int i = 0; i < 6; i++) {
            XinzhibaoNewModel news = new XinzhibaoNewModel("imageUrl", "商状元", "2017-01-06 11:46", false, "contentImageUrl", "小固然：原油沥青日内操作原理与建议原理与建议", 200, 2);
            xinzhibaoNewModelList.add(news);
        }
        recyclerviewAdapter.notifyDataSetChanged();
    }
}
