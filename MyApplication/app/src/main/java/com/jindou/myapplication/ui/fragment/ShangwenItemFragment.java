package com.jindou.myapplication.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.jindou.myapplication.R;
import com.jindou.myapplication.model.NewsModel;
import com.jindou.myapplication.ui.activity.NewsDetailActivity;
import com.jindou.myapplication.ui.adapter.SimpleAdapter;
import com.jindou.myapplication.ui.view.ShareAndCollectDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ShangWenItemFragment extends Fragment implements SimpleAdapter.OnItemClickListener {

    @BindView(R.id.recyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.xRefreshView)
    public XRefreshView xRefreshView;
    private SimpleAdapter recyclerviewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<NewsModel> personList;

    public ShangWenItemFragment() {
        // Required empty public constructor
    }

    public static ShangWenItemFragment newInstance() {
        ShangWenItemFragment fragment = new ShangWenItemFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shangwen_item, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        personList=new ArrayList<NewsModel>();
        initViewsAnDatas();

    }

    private void initViewsAnDatas() {
        //if true，data change can not change  recyclerView size
        recyclerView.setHasFixedSize(true);
//        recyclerView.addOnItemTouchListener();
        recyclerviewAdapter = new SimpleAdapter(personList,getActivity().getApplication(),ShangWenItemFragment.this);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
//        动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(recyclerviewAdapter);
//        加载更多
        recyclerviewAdapter.setCustomLoadMoreView(new XRefreshViewFooter(getContext()));
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
        personList.clear();
        for (int i = 0; i < 6; i++) {
            NewsModel news = new NewsModel("股笑笑:  大家可以一起随时查看", "新华网"+i
                    ,i,"综合资讯");
            personList.add(news);
        }
        recyclerviewAdapter.setData(personList);
    }
//    上拉加载更多数据
    private void loadMoreData() {
        for (int i = 0; i < 6; i++) {
            NewsModel news = new NewsModel("股笑笑:      大家可以查看新添加数据", "新华网"+i
                    ,i,"综合资讯");
            personList.add(news);
        }
        recyclerviewAdapter.setData(personList);
    }

    public void showDialog() {
        ShareAndCollectDialog myDialog = new ShareAndCollectDialog(getActivity(), Gravity.BOTTOM,R.style.ShareDialog);
        myDialog.show();
    }

    @Override
    public void share() {
        showDialog();
    }

    @Override
    public void goToDetailNews(int position) {
        Toast.makeText(getActivity(),"you clicked new item..."+position,Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(),NewsDetailActivity.class));
    }
}
