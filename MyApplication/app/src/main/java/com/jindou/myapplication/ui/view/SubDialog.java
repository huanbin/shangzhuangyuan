package com.jindou.myapplication.ui.view;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jindou.myapplication.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhishi on 2017/3/7.
 */

public class SubDialog extends CustomDialogCommon {
    @BindView(R.id.author_article_subscription)
    LinearLayout authorArticleSubscription;
    @BindView(R.id.author_article_collect)
    LinearLayout authorArticleCollect;
    @BindView(R.id.author_article_share_weixin_pengyouquan)
    LinearLayout authorArticleShareWeixinPengyouquan;
    @BindView(R.id.author_article_share_weixin_haoyou)
    LinearLayout authorArticleShareWeixinHaoyou;
    @BindView(R.id.author_article_share_mobile_qq)
    LinearLayout authorArticleShareMobileQq;
    @BindView(R.id.author_article_share_sina_weibo)
    LinearLayout authorArticleShareSinaWeibo;
    @BindView(R.id.bt_over_cancle)
    Button btOverCancle;

    public SubDialog(Context context, int gravity, int style) {
        super(context, R.layout.subscription_menu_dialog_view, gravity, style);
    }

    @OnClick({R.id.author_article_subscription, R.id.author_article_collect, R.id.author_article_share_weixin_pengyouquan, R.id.author_article_share_weixin_haoyou, R.id.author_article_share_mobile_qq, R.id.author_article_share_sina_weibo, R.id.bt_over_cancle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.author_article_subscription:
                break;
            case R.id.author_article_collect:
                break;
            case R.id.author_article_share_weixin_pengyouquan:
                break;
            case R.id.author_article_share_weixin_haoyou:
                break;
            case R.id.author_article_share_mobile_qq:
                break;
            case R.id.author_article_share_sina_weibo:
                break;
            case R.id.bt_over_cancle:
                if (isShowing()) {
                    dismiss();
                }
                break;
        }
    }
}
