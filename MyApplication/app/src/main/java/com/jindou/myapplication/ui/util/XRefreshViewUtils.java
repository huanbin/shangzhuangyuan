package com.jindou.myapplication.ui.util;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.jindou.myapplication.R;
import com.jindou.myapplication.model.NewsModel;
import java.util.List;

/**
 * Created by zhishi on 2017/3/8.
 */

public class XRefreshViewUtils {

    private static List<Object> modelDatas;

    public static void initViewsAnDatas(Context context, final XRefreshView xRefreshView, final RecyclerView recyclerView, final BaseRecyclerAdapter recyclerviewAdapter,List<Object> pModelDatas) {
        modelDatas=pModelDatas;
        //if true，data change can not change  recyclerView size
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
//        动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(recyclerviewAdapter);
//        加载更多
        recyclerviewAdapter.setCustomLoadMoreView(new XRefreshViewFooter(context));
//
        configXRfreshView(xRefreshView,new XRefreshView.SimpleXRefreshListener(){
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

                loadMoreData(recyclerviewAdapter,modelDatas);

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
                requestRecyclerViewData(recyclerviewAdapter,modelDatas);
            }
        });
//      请求数据
        requestRecyclerViewData(recyclerviewAdapter,modelDatas);
    }

    private static void setEmptyViewClickListener(XRefreshView xRefreshView, View.OnClickListener listener) {
        View emptyView = xRefreshView.getEmptyView();
        if (emptyView != null) {
            emptyView.setOnClickListener(listener);
        }
    }

    private static void configXRfreshView(XRefreshView xRefreshView, XRefreshView.SimpleXRefreshListener listener) {
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
    private static void requestRecyclerViewData(BaseRecyclerAdapter recyclerviewAdapter, List<Object> modelDatas) {
        modelDatas.clear();
        for (int i = 0; i < 6; i++) {
            NewsModel news = new NewsModel("股笑笑:  大家可以一起随时查看", "新华网"+i
                    ,i,"综合资讯");
            modelDatas.add(news);
        }
        recyclerviewAdapter.notifyDataSetChanged();
    }
    //    上拉加载更多数据
    private static void loadMoreData(BaseRecyclerAdapter recyclerviewAdapter, List<Object> modelDatas) {
        for (int i = 0; i < 6; i++) {
            NewsModel news = new NewsModel("股笑笑:      大家可以查看新添加数据", "新华网"+i
                    ,i,"综合资讯");
            modelDatas.add(news);
        }
        recyclerviewAdapter.notifyDataSetChanged();
    }
}
