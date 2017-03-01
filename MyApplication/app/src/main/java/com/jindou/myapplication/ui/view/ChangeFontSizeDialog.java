package com.jindou.myapplication.ui.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.jindou.myapplication.R;
import com.jindou.myapplication.ui.activity.NewsDetailActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhishi on 2017/2/28.
 */

public class ChangeFontSizeDialog extends CustomDialogCommon {
    @BindView(R.id.btSmallFont)
    public TextView btSmallFont;
    @BindView(R.id.btMediumFont)
    public TextView btMediumFont;
    @BindView(R.id.btLargeFont)
    public TextView btLargeFont;


    public ChangeFontSizeDialog(Context context, int gravity, int style) {
        super(context, R.layout.change_font_size_dialog_view, gravity, style);
    }

    @OnClick({R.id.btSmallFont,R.id.btMediumFont,R.id.btLargeFont})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.btSmallFont:
                ((NewsDetailActivity)mContext).setActivityFontSize(14);
                break;
            case R.id.btMediumFont:
                ((NewsDetailActivity)mContext).setActivityFontSize(18);
                break;
            case R.id.btLargeFont:
                ((NewsDetailActivity)mContext).setActivityFontSize(22);
                break;
            default:
                break;
        }
        if (isShowing()) {
            dismiss();
        }
    }
}
