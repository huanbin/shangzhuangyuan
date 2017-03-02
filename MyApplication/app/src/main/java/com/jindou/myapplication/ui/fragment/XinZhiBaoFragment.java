package com.jindou.myapplication.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jindou.myapplication.R;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class XinZhiBaoFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private AppCompatActivity activity;

    @BindView(R.id.xinzhibaoNew)
    public TextView xinzhibaoNew;
    @BindView(R.id.xinzhibaoSubscription)
    public TextView xinzhibaoSubscription;
    @BindView(R.id.ib_expand_or_collpase)
    public ImageButton ib_expand_or_collpase;
    @BindView(R.id.ly_need_expand)
    public LinearLayout ly_need_expand;


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
        ButterKnife.bind(this,view);
        setCurrent(0);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity= (AppCompatActivity) getActivity();
    }

    @OnClick(R.id.ib_expand_or_collpase)
    public void expandOrCollpase(View view){
        switch (view.getId()) {
            case R.id.ib_expand_or_collpase:
                boolean isShow=!(ly_need_expand.getVisibility()==View.VISIBLE?true:false);
                if (isShow) {
                    ly_need_expand.setVisibility(View.VISIBLE);
                    //动画
                    Animation expandAnimation = AnimationUtils.loadAnimation(activity, R.anim.xinzhibao_expand_animation);
                    ib_expand_or_collpase.startAnimation(expandAnimation);
                }else {
                    ly_need_expand.setVisibility(View.GONE);
                    //动画
                    Animation collapseAnimation=AnimationUtils.loadAnimation(activity,R.anim.xinzhibao_collapse_animation);
                    ib_expand_or_collpase.startAnimation(collapseAnimation);
                }
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.xinzhibaoNew,R.id.xinzhibaoSubscription})
    public void OnSelectItem(View view){
        clearState();
        switch (view.getId()) {
            case R.id.xinzhibaoNew:
                setCurrent(0);
                break;
            case R.id.xinzhibaoSubscription:
                setCurrent(1);
                break;
            default:
                break;
        }
    }
    private void setCurrent(int position){
        if (0==position) {
            xinzhibaoNew.setTextColor(Color.parseColor("#FFFFFFFF"));
            xinzhibaoNew.setBackgroundResource(R.drawable.shangji_button_selected);
        }else {
            xinzhibaoSubscription.setTextColor(Color.parseColor("#FFFFFFFF"));
            xinzhibaoSubscription.setBackgroundResource(R.drawable.shangji_button_selected);
        }
    }

    private void clearState(){
        xinzhibaoNew.setTextColor(Color.parseColor("#FF333333"));
        xinzhibaoNew.setBackgroundResource(R.drawable.shangji_button_normal);
        xinzhibaoSubscription.setTextColor(Color.parseColor("#FF333333"));
        xinzhibaoSubscription.setBackgroundResource(R.drawable.shangji_button_normal);
    }
}
