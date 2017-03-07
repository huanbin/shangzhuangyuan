package com.jindou.myapplication.ui.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.jindou.myapplication.R;
import com.jindou.myapplication.model.XinzhibaoNewModel;
import com.jindou.myapplication.ui.adapter.XinzhibaoNewAdapter;
import com.jindou.myapplication.ui.view.WrapperView;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class XinZhiBaoFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private final  static String[] pageTitles=new String[]{"动态","作者主页"};
    private Fragment[] subFragments=new Fragment[]{new XinzhibaoSubDynamicFragment(),new XinzhibaoSubAuthorFragment()};
    private String mParam1;
    private AppCompatActivity activity;
    private List<XinzhibaoNewModel> mDatas;
    private XinzhibaoNewAdapter adapter;

    @BindView(R.id.xinzhibaoNew)
    public TextView xinzhibaoNew;
    @BindView(R.id.xinzhibaoSubscription)
    public TextView xinzhibaoSubscription;
    @BindView(R.id.ib_expand_or_collpase)
    public ImageButton ib_expand_or_collpase;
    @BindView(R.id.ly_need_expand)
    public LinearLayout ly_need_expand;
    @BindView(R.id.xinzhibaoRecyclerView)
    public RecyclerView xinzhibaoRecyclerView;
    @BindView(R.id.xinzhibaoRefreshView)
    public XRefreshView xinzhibaoRefreshView;
    @BindView(R.id.xinzhibao_new_view)
    public View newView;
    @BindView(R.id.xinzhibao_subscription_view)
    public View subscriptionView;
    //我的订阅
    private boolean hasShowSubscription;
    @BindView(R.id.xinzhibao_subsription_viewpager)
    public ViewPager subsriptionViewPager;
    @BindView(R.id.xinzhibao_subscription_tablayout)
    public SmartTabLayout subscriptionTablayout;


    public XinZhiBaoFragment() {
        // Required empty public constructor
    }

    public static XinZhiBaoFragment newInstance(String param1) {
        XinZhiBaoFragment fragment = new XinZhiBaoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xinzhibao, container, false);
        ButterKnife.bind(this, view);
        setCurrent(0);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (AppCompatActivity) getActivity();
        initNewViews();
        initSubscriptionView();
        showNew();
    }

    private void initSubscriptionView() {

        subsriptionViewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return subFragments[position];
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return pageTitles[position];
            }
        });
        subscriptionTablayout.setViewPager(subsriptionViewPager);
    }

    private void initNewViews() {
        xinzhibaoRecyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        //xinzhibaoRecyclerView.addItemDecoration(new DividerItemDecoration(activity, OrientationHelper.VERTICAL));
        xinzhibaoRecyclerView.setItemAnimator(new DefaultItemAnimator());
        xinzhibaoRecyclerView.setHasFixedSize(true);
        mDatas = new ArrayList<>();
        adapter = new XinzhibaoNewAdapter(mDatas, activity);
        xinzhibaoRecyclerView.setAdapter(adapter);
        //        加载更多
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(getContext()));
        configXRfreshView(xinzhibaoRefreshView, new XRefreshView.SimpleXRefreshListener() {
            //          下拉刷新
            @Override
            public void onRefresh() {
                super.onRefresh();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xinzhibaoRefreshView.stopRefresh();
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
                        xinzhibaoRefreshView.stopLoadMore();
                    }
                }, 1000);
            }
        });
//      为空View添加点击事件
        setEmptyViewClickListener(xinzhibaoRefreshView, new View.OnClickListener() {
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

    private void loadMoreData() {
        if (mDatas != null) {
            for (int i = 0; i < 3; i++) {
                mDatas.add(new XinzhibaoNewModel());
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void requestRecyclerViewData() {
        if (mDatas != null) {
            mDatas.clear();
            for (int i = 0; i < 6; i++) {
                mDatas.add(new XinzhibaoNewModel());
            }
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 数据加载管理者XRefreshView
     *
     * @param xRefreshView
     * @param listener
     */
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


    @OnClick(R.id.ib_expand_or_collpase)
    public void expandOrCollpase(View view) {
        switch (view.getId()) {
            case R.id.ib_expand_or_collpase:
                boolean isShow = !(ly_need_expand.getVisibility() == View.VISIBLE ? true : false);
                if (isShow) {
                    ly_need_expand.setVisibility(View.VISIBLE);
                    //动画
                    Animation expandAnimation = AnimationUtils.loadAnimation(activity, R.anim.xinzhibao_expand_animation);
                    ib_expand_or_collpase.startAnimation(expandAnimation);
                } else {
                    ly_need_expand.setVisibility(View.GONE);
                    //动画
                    Animation collapseAnimation = AnimationUtils.loadAnimation(activity, R.anim.xinzhibao_collapse_animation);
                    ib_expand_or_collpase.startAnimation(collapseAnimation);
                }
                break;
            default:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.xinzhibaoNew, R.id.xinzhibaoSubscription})
    public void OnSelectItem(View view) {
        clearState();
        switch (view.getId()) {
            case R.id.xinzhibaoNew:
                setCurrent(0);
                showNew();
//                startAnimator(view);
                break;
            case R.id.xinzhibaoSubscription:
                setCurrent(1);
                showSubscription();
//                startAnimator(view);
                break;
            default:
                break;
        }
    }
    //stateAnimator
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void startAnimator(View view) {
        WrapperView wrapperView=new WrapperView(view);
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator backgroundAnimator = ObjectAnimator.ofArgb(wrapperView, "background", Color.parseColor("#FFF09E0A"));
        backgroundAnimator.setDuration(1000);
//        //默认RESTART
//        backgroundAnimator.setRepeatMode(ValueAnimator.RESTART);
//        //默认为0
//        backgroundAnimator.setRepeatCount(0);
        ObjectAnimator translateAnimator = ObjectAnimator.ofFloat(wrapperView, "alpha", 0, 1.0f);
        translateAnimator.setDuration(1000);

        set.playTogether(backgroundAnimator, translateAnimator);
        set.start();
    }

    /**
     * 最新
     */
    private void showNew() {
        setVisible(true);
    }

    private void setVisible(boolean isVisible) {
        newView.setVisibility(isVisible?View.VISIBLE:View.GONE);
        subscriptionView.setVisibility(!isVisible?View.VISIBLE:View.GONE);
    }

    /**
     * 我的订阅
     */
    private void showSubscription() {
        setVisible(false);
    }

    private void setCurrent(int position) {
        if (0 == position) {
            xinzhibaoNew.setTextColor(Color.parseColor("#FFFFFFFF"));
            xinzhibaoNew.setBackgroundResource(R.drawable.shangji_button_selected);
        } else {
            xinzhibaoSubscription.setTextColor(Color.parseColor("#FFFFFFFF"));
            xinzhibaoSubscription.setBackgroundResource(R.drawable.shangji_button_selected);
        }
    }

    private void clearState() {
        xinzhibaoNew.setTextColor(Color.parseColor("#FF333333"));
        xinzhibaoNew.setBackgroundResource(R.drawable.shangji_button_normal);
        xinzhibaoSubscription.setTextColor(Color.parseColor("#FF333333"));
        xinzhibaoSubscription.setBackgroundResource(R.drawable.shangji_button_normal);
    }
}
