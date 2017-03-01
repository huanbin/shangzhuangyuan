package com.jindou.myapplication.ui.view;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jindou.myapplication.R;
import com.jindou.myapplication.ui.activity.NewsDetailActivity;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by zhishi on 2017/2/28.
 */
//商闻等导航栏溢出菜单
public class OverflowMenuDialog extends CustomDialogCommon {
    @BindView(R.id.over_share_weixin_pengyouquan)
    public LinearLayout over_share_weixin_pengyouquan;
    @BindView(R.id.over_share_weixin_haoyou)
    public LinearLayout over_share_weixin_haoyou;
    @BindView(R.id.over_share_mobile_qq)
    public LinearLayout over_share_mobile_qq;
    @BindView(R.id.over_share_sina_weibo)
    public LinearLayout over_share_sina_weibo;
    @BindView(R.id.over_collect)
    public LinearLayout over_collect;
    @BindView(R.id.over_change_font_size)
    public LinearLayout over_change_font_size;
    @BindView(R.id.over_night_mode)
    public LinearLayout over_night_mode;

    @BindView(R.id.bt_over_cancle)
    public Button bt_over_cancle;
    @BindView(R.id.over_collect_image)
    public ImageView over_collect_image;
    @BindView(R.id.overTvCollect)
    public TextView overTvCollect;

    public ICollectedListener listener;
    private static boolean isCollected=false;

    @OnClick({R.id.over_share_weixin_pengyouquan,R.id.over_share_weixin_haoyou,R.id.over_share_mobile_qq,R.id.over_share_sina_weibo,R.id.over_collect,R.id.over_change_font_size,R.id.bt_over_cancle})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.over_share_weixin_pengyouquan:
                break;
            case R.id.over_share_weixin_haoyou:
                break;
            case R.id.over_share_mobile_qq:
                break;
            case R.id.over_share_sina_weibo:
                break;
            case R.id.over_collect:
                if (isCollected) {
                    isCollected=false;
                    over_collect_image.setImageResource(R.drawable.nocollection);
                    overTvCollect.setText(R.string.collect);
                }else {
                    isCollected=true;
                    over_collect_image.setImageResource(R.drawable.collection);
                    overTvCollect.setText(R.string.cancle_collect);
                }
                ((NewsDetailActivity)mContext).setActivityCollected(isCollected);
                break;
            case R.id.over_change_font_size:
                Timber.d("ckicked change font size...");
                if (isShowing()) {
                    dismiss();
                }
                ChangeFontSizeDialog dialog=new ChangeFontSizeDialog((NewsDetailActivity)mContext, Gravity.BOTTOM,R.style.ShareDialog);
                dialog.show();
//                ((NewsDetailActivity)mContext).setActivityFontSize(32);
                break;
            case R.id.bt_over_cancle:
                if (isShowing()) {
                    dismiss();
                }
                break;
            case R.id.over_night_mode:
                break;
            default:
                break;
        }
    }

    public OverflowMenuDialog(Context context, int gravity, int style) {
        super(context, R.layout.overflow_menu_dialog_view, gravity, style);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener= (ICollectedListener) mContext;
        isCollected=listener.setDialogCollected();
        if (isCollected) {
            over_collect_image.setImageResource(R.drawable.collection);
            overTvCollect.setText(R.string.cancle_collect);
        }else {
            over_collect_image.setImageResource(R.drawable.nocollection);
            overTvCollect.setText(R.string.collect);
        }
    }
    public interface ICollectedListener{
        boolean setDialogCollected();
    }
}
