package com.jindou.myapplication.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.jindou.myapplication.R;
import com.jindou.myapplication.model.UserDynamicModel;
import com.jindou.myapplication.ui.view.CircleImageView;

import java.util.List;

/**
 * Created by zhishi on 2017/3/3.
 */

public class XinzhibaoSubAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder> {

    private List<UserDynamicModel> datas;
    private Context context;

    public XinzhibaoSubAdapter(List<UserDynamicModel> datas, Context context) {
        this.datas = datas;
        this.context = context;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, boolean isItem) {

    }

    @Override
    public int getAdapterItemCount() {
        return datas.size();
    }

    public class DynamicViewHolder extends RecyclerView.ViewHolder{

        public CircleImageView circleUserIcon;
        public TextView title;
        public TextView content;

        public DynamicViewHolder(View itemView) {
            super(itemView);
            circleUserIcon= (CircleImageView) itemView.findViewById(R.id.xinzhibao_sub_user_icon);
            title= (TextView) itemView.findViewById(R.id.xinzhibao_sub_title);
            content= (TextView) itemView.findViewById(R.id.xinzhibao_sub_content);
        }
    }

}
