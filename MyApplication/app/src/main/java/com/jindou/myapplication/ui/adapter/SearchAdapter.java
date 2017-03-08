package com.jindou.myapplication.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.jindou.myapplication.R;
import com.jindou.myapplication.ui.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhishi on 2017/3/8.
 */

public class SearchAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder> {

    private List<Object> datas;
    private Context context;

    public SearchAdapter(List<Object> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view) {
        return new MyViewHold(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_search, parent, false);
        return new MyViewHold(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position, boolean isItem) {
        MyViewHold myViewHold = (MyViewHold) holder;
        myViewHold.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show(context, "clicked item position=" + position);
            }
        });
        //<font color=\'#F09E0A\'>商状元</font>
        Spanned spanned = Html.fromHtml("<font color=\\'#F09E0A\\'>商状元</font>");
        myViewHold.searchArticleTitle.setText(String.format("萧固然: 原油%s内操作原理与建议操作原理与建议", spanned));
        myViewHold.searchArticleSource.setText("新华网");
        myViewHold.searchArticleTime.setText("10小时前");
    }

    @Override
    public int getAdapterItemCount() {
        return datas.size();
    }

    public class MyViewHold extends RecyclerView.ViewHolder {
        TextView searchArticleTitle;
        TextView searchArticleSource;
        TextView searchArticleTime;

        public MyViewHold(View itemView) {
            super(itemView);
            searchArticleTitle = (TextView) itemView.findViewById(R.id.search_article_title);
            searchArticleSource = (TextView) itemView.findViewById(R.id.search_article_source);
            searchArticleTime = (TextView) itemView.findViewById(R.id.search_article_time);
        }
    }
}
