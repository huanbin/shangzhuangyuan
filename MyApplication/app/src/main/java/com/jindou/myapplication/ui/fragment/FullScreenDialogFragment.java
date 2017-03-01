package com.jindou.myapplication.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jindou.myapplication.R;
import com.jindou.myapplication.model.ImageModel;
import com.jindou.myapplication.ui.activity.TestActivity;
import com.jindou.myapplication.ui.adapter.FragmentImageAdapter;
import com.jindou.myapplication.ui.adapter.PicturePagerAdapter;
import com.jindou.myapplication.ui.view.ScanPicturesDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/25.
 */

public class FullScreenDialogFragment extends DialogFragment implements View.OnClickListener {
    private int mClickItem;//对应显示ViewPager子项的位置
    private List<String> mListImgUrls;
    private ViewPager mViewPager;
    public ImageView ivSavePicture;
    private Integer[] mImgIds;//本地图片资源ID
    private ScanPicturesDialog mDialog;
    public static final String TAG_NAME = FullScreenDialogFragment.class.getName();
    private static String KEY_IMAGES="page_images";
    private Context mContext;

    //即学即用的工厂方法
    public static FullScreenDialogFragment newInstance(Context context, List<String> images, int clickItem) {
        Bundle args = new Bundle();
        FullScreenDialogFragment fragment = new FullScreenDialogFragment();
        args.putStringArrayList(KEY_IMAGES, (ArrayList<String>) images);
        fragment.setArguments(args);
        fragment.mContext = context;
        fragment.mImgIds = new Integer[]{R.mipmap.ic_launcher, R.drawable.savepicture, R.drawable.adviseimage, R.drawable.img_equipment};
        fragment.mClickItem = clickItem;
        return fragment;
    }

    //由ViewPager来响应点击
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivShowPicture:
                this.dismiss();
                break;
            case R.id.ivSavePicture:
                Toast.makeText(mContext, "save picture.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mListImgUrls=getArguments().getStringArrayList(KEY_IMAGES);
        mDialog = new ScanPicturesDialog(getFragmentManager(),getActivity(),mListImgUrls);
        mViewPager = (ViewPager) mDialog.getCustomView().findViewById(R.id.viewPagerScanPicture);
        ivSavePicture = (ImageView) mDialog.getCustomView().findViewById(R.id.ivSavePicture);
        ivSavePicture.setOnClickListener(this);
        initViewPager();
        return mDialog;
    }

    private void initViewPager() {
//        if (mImgIds != null && mImgIds.length > 0) {
//            List<View> listImgs = new ArrayList<>();
//            for (int i = 0; i < mImgIds.length; i++) {
//                View view = LayoutInflater.from(mContext).inflate(R.layout.scan_picture_dialog_view, null);
//                ImageView ivShowPicture = (ImageView) view.findViewById(R.id.ivShowPicture);
//                ivShowPicture.setImageResource(mImgIds[i]);
//                listImgs.add(view);
//                ivShowPicture.setOnClickListener(this);
//            }
//            if (listImgs.size() > 0) {
//                PicturePagerAdapter pageAdapter = new PicturePagerAdapter(listImgs);
//                mViewPager.setAdapter(pageAdapter);
//                mViewPager.setCurrentItem(mClickItem);
//            }
//        }

        FragmentImageAdapter adapter=new FragmentImageAdapter(getFragmentManager(),mContext,mListImgUrls);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);

    }
}

