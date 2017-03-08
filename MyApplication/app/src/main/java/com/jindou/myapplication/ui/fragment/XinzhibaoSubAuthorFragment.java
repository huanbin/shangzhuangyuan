package com.jindou.myapplication.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.jindou.myapplication.R;
import com.jindou.myapplication.model.UserDynamicModel;
import com.jindou.myapplication.ui.adapter.XinzhibaoSubAdapter;
import com.jindou.myapplication.ui.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhishi on 2017/3/3.
 */

public class XinzhibaoSubAuthorFragment extends Fragment {

    private BaseRecyclerAdapter recyclerAdapter;
    @BindView(R.id.xRefreshView)
    public XRefreshView xRefreshView;
    @BindView(R.id.recyclerView)
    public RecyclerView recyclerView;

    private AppCompatActivity activity;
    private List<UserDynamicModel> datas;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shangwen_item, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (AppCompatActivity) getActivity();
        datas = new ArrayList<>();
        initViewsAnDatas();
    }

    private float lastX;
    private int scaledTouchSlop;

    private void initViewsAnDatas() {
        scaledTouchSlop = ViewConfiguration.get(activity).getScaledTouchSlop();
        //if true，data change can not change  recyclerView size
        recyclerView.setHasFixedSize(true);
        recyclerAdapter = new XinzhibaoSubAdapter(datas, activity);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
//        动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(recyclerAdapter);
//        加载更多
        recyclerAdapter.setCustomLoadMoreView(new XRefreshViewFooter(getContext()));
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

            //上拉加载更多
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
        xRefreshView.setEmptyView(R.layout.layout_emptyview);
        xRefreshView.setXRefreshViewListener(listener);
    }

    //  初始化RecyclerView数据
    private void requestRecyclerViewData() {
        if (datas != null) {
            datas.clear();
            for (int i = 0; i < 6; i++) {
                datas.add(new UserDynamicModel());
            }
            recyclerAdapter.notifyDataSetChanged();
        }
    }

    //    上拉加载更多数据
    private void loadMoreData() {
        if (datas != null) {
            for (int i = 0; i < 3; i++) {
                datas.add(new UserDynamicModel());
            }
            recyclerAdapter.notifyDataSetChanged();
        }
    }


}
