package com.jindou.myapplication.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.jindou.myapplication.R;
import com.jindou.myapplication.model.XinzhibaoNewModel;
import com.jindou.myapplication.ui.activity.AuthorArticleDetail;
import com.jindou.myapplication.ui.view.ShareDialog;

import java.util.List;

/**
 * Created by zhishi on 2017/3/6.
 */

public class AuthorArticleAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder> {

    public static String AUTHOR_ARTICLE="author_article";
    private List<XinzhibaoNewModel> xinzhibaoNewModelList;
    private Context context;

    public AuthorArticleAdapter(List<XinzhibaoNewModel> xinzhibaoNewModelList, Context context) {
        this.xinzhibaoNewModelList = xinzhibaoNewModelList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view) {
        return new NewUserArticleViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycleview_xinzhibao_user_article, parent, false);
        NewUserArticleViewHolder holder=new NewUserArticleViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position, boolean isItem) {
        NewUserArticleViewHolder newUserArticleViewHolder= (NewUserArticleViewHolder) holder;
        newUserArticleViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, AuthorArticleDetail.class);
                intent.putExtra(AUTHOR_ARTICLE,xinzhibaoNewModelList.get(position));
                context.startActivity(intent);
            }
        });

        newUserArticleViewHolder.userAvtar.setImageResource(R.drawable.qq);
        newUserArticleViewHolder.userName.setText("商状元");
        newUserArticleViewHolder.date.setText("2017-01-06");
        newUserArticleViewHolder.time.setText("11:46");
        newUserArticleViewHolder.image.setImageResource(R.drawable.xinzhibao_new);
        newUserArticleViewHolder.title.setText("萧固然：原油沥青日内操作原理与建议原理与建议");
        newUserArticleViewHolder.readCount.setText(String.valueOf(200));
        newUserArticleViewHolder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShareDialog(context, Gravity.BOTTOM,R.style.ShareDialog).show();
            }
        });
    }

    @Override
    public int getAdapterItemCount() {
        return xinzhibaoNewModelList.size();
    }

    public class NewUserArticleViewHolder extends RecyclerView.ViewHolder {

        public ImageView userAvtar;
        public TextView userName;
        public TextView date;
        public TextView time;
        public ImageButton subscription;
        public ImageView image;
        public TextView title;
        public TextView readCount;
        public ImageButton share;
        public View view;

        public NewUserArticleViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            userAvtar = (ImageView) itemView.findViewById(R.id.xinzhibao_avtar);
            userName = (TextView) itemView.findViewById(R.id.xinzhibao_user_name);
            date = (TextView) itemView.findViewById(R.id.xinzhibao_date);
            time = (TextView) itemView.findViewById(R.id.xinzhibao_time);
            image = (ImageView) itemView.findViewById(R.id.xinzhibao_content_image);
            title = (TextView) itemView.findViewById(R.id.xinzhibao_title);
            readCount = (TextView) itemView.findViewById(R.id.xinzhibao_read_count);
            share= (ImageButton) itemView.findViewById(R.id.xinzhibao_user_article_share);
        }
    }
}
