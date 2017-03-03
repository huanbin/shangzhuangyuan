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
import com.jindou.myapplication.model.NewsModel;

import java.util.List;

public class SimpleAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder> {
    private List<NewsModel> list;
    private Context mContext;
    private OnItemClickListener iShare;

    public SimpleAdapter(List<NewsModel> list, Context context, OnItemClickListener iShare) {
        this.list = list;
        this.mContext = context;
        this.iShare = iShare;
    }

    public void setData(List<NewsModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getAdapterItemCount() {
        return list.size();
    }

    // TODO: 2017/2/20
    @Override
    public RecyclerView.ViewHolder getViewHolder(View view) {
        int itemViewType = getItemViewType(view.getId());
        if (itemViewType == 1) {
            return new SingleImageViewHolder(view);
        } else if (itemViewType == 2) {
            return new MoreImageViewHolder(view);
        } else if (itemViewType == 3) {
            return new AdviseViewHolder(view);
        } else if (itemViewType==4) {
            return new NoImageViewHolder(view);
        }else {
            return new NoImageViewHolder(view);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        RecyclerView.ViewHolder holder = null;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == 1) {
            View v = layoutInflater.inflate(
                    R.layout.item_recylerview_single_image, parent, false);
            holder = new SingleImageViewHolder(v);
        } else if (viewType == 2) {
            View v = layoutInflater.inflate(
                    R.layout.item_recylerview_more_image, parent, false);
            holder = new MoreImageViewHolder(v);
        } else if (viewType == 3) {
            View v = layoutInflater.inflate(
                    R.layout.item_recylerview_advise_image, parent, false);
            holder = new AdviseViewHolder(v);
        } else if (viewType==4){
            View v = layoutInflater.inflate(
                    R.layout.item_recylerview_no_image, parent, false);
            holder = new NoImageViewHolder(v);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position, boolean isItem) {

        NewsModel news = list.get(position);
        int type = getItemViewType(position);
        if (type == 1) {
            SingleImageViewHolder holder1 = (SingleImageViewHolder) holder;
            holder1.titleTv.setText(news.getTitle());
            holder1.typeTv.setText(news.getType() + "");
            holder1.sourceTv.setText(news.getSource());
//            holder1.shareIb.setImageResource(R.drawable.share_collect);
            holder1.shareIb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (iShare != null) {
                        iShare.share();
                    }
                }
            });
//            int color = (int) Math.floor(Math.random() * 255);
//            ColorDrawable colorDrawable = new ColorDrawable(Color.argb(color, 125, 125, 125));
            holder1.picIv.setImageResource(R.drawable.singleimage);
            holder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iShare.goToDetailNews(position);
                }
            });
        } else if (type == 2) {
            MoreImageViewHolder holder2 = (MoreImageViewHolder) holder;
            holder2.titleTv.setText(news.getTitle());
            holder2.typeTv.setText(news.getType() + "");
            holder2.sourceTv.setText(news.getSource());
//            holder2.shareIb.setImageResource(R.drawable.share_collect);
            holder2.shareIb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (iShare != null) {
                        iShare.share();
                    }
                }
            });
            holder2.picIv1.setImageResource(R.drawable.singleimage);
            holder2.picIv2.setImageResource(R.drawable.singleimage);
            holder2.picIv3.setImageResource(R.drawable.singleimage);
            holder2.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iShare.goToDetailNews(position);
                }
            });
        } else if (type == 3) {
            AdviseViewHolder holder3 = (AdviseViewHolder) holder;
            holder3.tvAdviseTitle.setText(news.getTitle());
            holder3.tvAdviseType.setText(news.getType() + "");
//            holder3.ibAdvise
            holder3.ibAdviseShare.setImageResource(R.drawable.share_collect);
            holder3.ibAdviseShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (iShare != null) {
                        iShare.share();
                    }
                }
            });
            holder3.ivAdviseImage.setImageResource(R.drawable.adviseimage);
            holder3.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iShare.goToDetailNews(position);
                }
            });
        } else {
            NoImageViewHolder holder4 = (NoImageViewHolder) holder;
            holder4.titleTvNoImg.setText(news.getTitle());
            holder4.typeTvNoImg.setText(news.getType() + "");
            holder4.sourceTvNoImg.setText(news.getSource());
//            holder4.shareIbNoImg.setImageResource(R.drawable.share_collect);
            holder4.shareIbNoImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (iShare != null) {
                        iShare.share();
                    }
                }
            });
            holder4.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iShare.goToDetailNews(position);
                }
            });
        }
    }

    @Override
    public int getAdapterItemViewType(int position) {
        if (position % 4 == 1) {
            return 1;
        } else if (position % 4 == 2) {
            return 2;
        } else if (position % 4 == 3) {
            return 3;
        } else {
            return 4;
        }
    }

    public class NoImageViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTvNoImg;
        public TextView typeTvNoImg;
        public TextView sourceTvNoImg;
        public ImageButton shareIbNoImg;
        public View itemView;

        public NoImageViewHolder(View view) {
            super(view);
            titleTvNoImg = (TextView) view
                    .findViewById(R.id.tvTitleNoImg);
            typeTvNoImg = (TextView) view
                    .findViewById(R.id.tvTypeNoImg);
            sourceTvNoImg = (TextView) view
                    .findViewById(R.id.tvSourceNoImg);
            shareIbNoImg = (ImageButton) view.findViewById(R.id.ibShareNoImg);
            itemView = view;
        }
    }


    public class SingleImageViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTv;
        public TextView typeTv;
        public TextView sourceTv;
        public ImageView picIv;
        public ImageButton shareIb;
        public View itemView;

        public SingleImageViewHolder(View view) {
            super(view);
            titleTv = (TextView) view
                    .findViewById(R.id.tvTitle);
            typeTv = (TextView) view
                    .findViewById(R.id.tvType);
            sourceTv = (TextView) view
                    .findViewById(R.id.tvSource);
            shareIb = (ImageButton) view.findViewById(R.id.ibShare);
            picIv = (ImageView) view.findViewById(R.id.ivPicture);
            itemView = view;
        }
    }

    public class MoreImageViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTv;
        public TextView typeTv;
        public TextView sourceTv;
        public ImageView picIv1;
        public ImageView picIv2;
        public ImageView picIv3;
        public ImageButton shareIb;
        public View itemView;

        public MoreImageViewHolder(View view) {
            super(view);
            titleTv = (TextView) view
                    .findViewById(R.id.tvTitleMore);
            typeTv = (TextView) view
                    .findViewById(R.id.tvTypeMore);
            sourceTv = (TextView) view
                    .findViewById(R.id.tvSourceMore);
            shareIb = (ImageButton) view.findViewById(R.id.ibShareMore);
            picIv1 = (ImageView) view.findViewById(R.id.ivPicMore1);
            picIv2 = (ImageView) view.findViewById(R.id.ivPicMore2);
            picIv3 = (ImageView) view.findViewById(R.id.ivPicMore3);
            itemView = view;
        }
    }

    private class AdviseViewHolder extends RecyclerView.ViewHolder {
        public TextView tvAdviseTitle;
        public TextView tvAdviseType;
        public ImageButton ibAdvise;
        public ImageView ivAdviseImage;
        public ImageButton ibAdviseShare;
        public View itemView;

        public AdviseViewHolder(View v) {
            super(v);
            tvAdviseTitle = (TextView) v.findViewById(R.id.tvAdviseTitle);
            ibAdvise = (ImageButton) v.findViewById(R.id.ibAdvise);
            tvAdviseType = (TextView) v.findViewById(R.id.tvAdviseType);
            ivAdviseImage = (ImageView) v.findViewById(R.id.ivAdviseImage);
            ibAdviseShare = (ImageButton) v.findViewById(R.id.ibAdviseShare);
            itemView = v;
        }
    }

    public interface OnItemClickListener {
        public void share();

        public void goToDetailNews(int position);
    }
}