package com.jindou.myapplication.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jindou.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShareAndCollectDialog extends CustomDialogCommon {
    @BindView(R.id.ly_share_collect)
    public LinearLayout ly_share_collect;
    @BindView(R.id.ly_share_mobile_qq)
    public LinearLayout ly_share_mobile_qq;
    @BindView(R.id.ly_share_weixin_pengyouquan)
    public LinearLayout ly_share_weixin_pengyouquan;
    @BindView(R.id.ly_share_weixin_haoyou)
    public LinearLayout ly_share_weixin_haoyou;
    @BindView(R.id.ly_share_sina_weibo)
    public LinearLayout ly_share_sina_weibo;
    @BindView(R.id.bt_share_cancle)
    public Button bt_share_cancle;

    public ShareAndCollectDialog(Context context, int gravity, int style) {
        super(context, R.layout.share_collect_dialog_view, gravity,style);
    }

    @OnClick({R.id.ly_share_collect,R.id.ly_share_mobile_qq,
            R.id.ly_share_weixin_pengyouquan,R.id.ly_share_weixin_haoyou,
            R.id.ly_share_sina_weibo,R.id.bt_share_cancle
    })
    public void onClick(View pView){
        switch (pView.getId()) {
            case R.id.ly_share_collect:
                break;
            case R.id.ly_share_mobile_qq:
                break;
            case R.id.ly_share_weixin_pengyouquan:
                break;
            case R.id.ly_share_weixin_haoyou:
                break;
            case R.id.ly_share_sina_weibo:
                break;
            case R.id.bt_share_cancle:
                if (isShowing()) {
                    dismiss();
                }
                break;
        }
    }

}