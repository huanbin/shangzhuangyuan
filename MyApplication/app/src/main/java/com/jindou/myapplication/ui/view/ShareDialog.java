package com.jindou.myapplication.ui.view;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.jindou.myapplication.R;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhishi on 2017/2/28.
 */

public class ShareDialog extends CustomDialogCommon {
    @BindView(R.id.ly_share_weixin_pengyouquan)
    public LinearLayout mWxPengyouQuan;
    @BindView(R.id.ly_share_weixin_haoyou)
    public LinearLayout mWxHaoyou;
    @BindView(R.id.ly_share_mobile_qq)
    public LinearLayout mMobileqq;
    @BindView(R.id.ly_share_sina_weibo)
    public LinearLayout mSinaWeibo;
    @BindView(R.id.bt_share_cancle)
    public Button mCancleShare;


    public ShareDialog(Context context, int gravity, int style) {
        super(context, R.layout.share_dialog_view, gravity, style);
    }

    @OnClick({R.id.ly_share_weixin_pengyouquan,R.id.ly_share_weixin_haoyou,R.id.ly_share_mobile_qq,R.id.ly_share_sina_weibo,R.id.bt_share_cancle})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.ly_share_weixin_pengyouquan:
                break;
            case R.id.ly_share_weixin_haoyou:
                break;
            case R.id.ly_share_mobile_qq:
                break;
            case R.id.ly_share_sina_weibo:
                break;
            case R.id.bt_share_cancle:
                if (isShowing()) {
                    dismiss();
                }
                break;
            default:
                break;
        }
    }
}

