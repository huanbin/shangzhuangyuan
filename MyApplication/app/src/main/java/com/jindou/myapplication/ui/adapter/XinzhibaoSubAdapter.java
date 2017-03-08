package com.jindou.myapplication.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.method.Touch;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.jindou.myapplication.R;
import com.jindou.myapplication.model.UserDynamicModel;
import com.jindou.myapplication.ui.activity.AuthorActivity;
import com.jindou.myapplication.ui.util.ToastUtil;
import com.jindou.myapplication.ui.view.CircleImageView;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

import java.util.List;

import timber.log.Timber;

/**
 * Created by zhishi on 2017/3/3.
 */

public class XinzhibaoSubAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder> {

    private List<UserDynamicModel> datas;
    private Context context;
    private final int scaledTouchSlop;
    private GestureDetector gestureDetector;
    private static GestureDetector.OnGestureListener onGestureListener;
    private float lastX;

    public XinzhibaoSubAdapter(List<UserDynamicModel> datas, Context context) {
        this.datas = datas;
        this.context = context;
        scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view) {
        return new DynamicViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycleview_xinzhibao_sub, parent, false);
        return new DynamicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position, boolean isItem) {
        final DynamicViewHolder dynamicViewHolder = (DynamicViewHolder) holder;
        dynamicViewHolder.item.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Log.d("TAG","scaledTouchSlop="+scaledTouchSlop);
                float x = event.getX();
                boolean isConsume = false;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float deltaX = x - lastX;
//                        Log.d("TAG", "deltaX=" + deltaX);
//                        Log.d("TAG", "abs deltaX=" + Math.abs(deltaX));
                        if (Math.abs(deltaX) > scaledTouchSlop) {
                            dynamicViewHolder.cancle.setVisibility(View.VISIBLE);
                            isConsume = true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    default:
                        break;
                }
                lastX = x;
                return isConsume;
            }
        });
        dynamicViewHolder.cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show(context, "取消订阅...");
                dynamicViewHolder.cancle.setVisibility(View.GONE);
            }
        });
        dynamicViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AuthorActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getAdapterItemCount() {
        return datas.size();
    }

    public class DynamicViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView circleUserIcon;
        public TextView title;
        public TextView content;
        public TextView cancle;
        public View item;

        public DynamicViewHolder(View itemView) {
            super(itemView);
            circleUserIcon = (CircleImageView) itemView.findViewById(R.id.xinzhibao_sub_user_icon);
            title = (TextView) itemView.findViewById(R.id.xinzhibao_sub_title);
            content = (TextView) itemView.findViewById(R.id.xinzhibao_sub_content);
            cancle = (TextView) itemView.findViewById(R.id.xinzhibao_sub_cancle);
            item = itemView.findViewById(R.id.item);
        }
    }
}
