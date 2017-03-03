package com.jindou.myapplication.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.jindou.myapplication.R;
import com.jindou.myapplication.model.XinzhibaoNewModel;
import com.jindou.myapplication.ui.util.ToastUtil;

import java.util.List;

/**
 * Created by zhishi on 2017/3/3.
 */

public class XinzhibaoNewAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder> {

    private List<XinzhibaoNewModel> mDatas;
    private Context mContext;
    private boolean isSubsrciption;

    public XinzhibaoNewAdapter(List<XinzhibaoNewModel> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view) {
        return new NewViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycleview_xinzhibao_new, parent, false);
        RecyclerView.ViewHolder holder = new NewViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position, boolean isItem) {
        XinzhibaoNewModel newModel = mDatas.get(position);
        final NewViewHolder viewHolder = (NewViewHolder) holder;
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show(mContext, "you clicked xinzhibao item:" + position);
            }
        });
        // TODO: 2017/3/3
        viewHolder.userAvtar.setImageResource(R.drawable.qq);
        viewHolder.userName.setText("商状元");
        // TODO: 2017/3/3
        viewHolder.date.setText("2017-01-06");
        viewHolder.time.setText("11:46");
        if (position % 2 == 0) {
            viewHolder.subscription.setImageResource(R.drawable.subscription_already);
        } else {
            viewHolder.subscription.setImageResource(R.drawable.subscription);
        }
        viewHolder.subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSubsrciption = !isSubsrciption;
                if (isSubsrciption) {
                    viewHolder.subscription.setImageResource(R.drawable.subscription_already);
                } else {
                    viewHolder.subscription.setImageResource(R.drawable.subscription);
                }
            }
        });

//        if (newModel.isSubscription()) {
//            viewHolder.subscription.setImageResource(R.drawable.subscription_already);
//        }else {
//            viewHolder.subscription.setImageResource(R.drawable.subscription);
//        }
        viewHolder.image.setImageResource(R.drawable.xinzhibao_new);
        viewHolder.title.setText("顾萧然: 原油沥青日内操作原理讲解原理讲解原理讲解");
        viewHolder.readCount.setText(String.valueOf(260));
    }

    @Override
    public int getAdapterItemCount() {
        return mDatas.size();
    }

    public class NewViewHolder extends RecyclerView.ViewHolder {

        public ImageView userAvtar;
        public TextView userName;
        public TextView date;
        public TextView time;
        public ImageButton subscription;
        public ImageView image;
        public TextView title;
        public TextView readCount;
        public View view;

        public NewViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            userAvtar = (ImageView) itemView.findViewById(R.id.xinzhibao_avtar);
            userName = (TextView) itemView.findViewById(R.id.xinzhibao_user_name);
            date = (TextView) itemView.findViewById(R.id.xinzhibao_date);
            time = (TextView) itemView.findViewById(R.id.xinzhibao_time);
            subscription = (ImageButton) itemView.findViewById(R.id.xinzhibao_subscription);
            image = (ImageView) itemView.findViewById(R.id.xinzhibao_content_image);
            title = (TextView) itemView.findViewById(R.id.xinzhibao_title);
            readCount = (TextView) itemView.findViewById(R.id.xinzhibao_read_count);
        }
    }
}
