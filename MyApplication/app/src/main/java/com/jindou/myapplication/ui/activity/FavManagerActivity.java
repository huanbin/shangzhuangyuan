package com.jindou.myapplication.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.jindou.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;
import timber.log.Timber;

/**
 * Created by zhishi on 2017/2/21.
 */

public class FavManagerActivity extends BaseTitleActivity {
    @BindView(R.id.tagViewLayout1)
    public TagContainerLayout mTagContainerLayout;
    @BindView(R.id.tagViewLayout2)
    public TagContainerLayout mTagContainerLayout2;
    private List<String> tags_top;
    private List<String> tags_bottom;
    private TagView.OnTagClickListener tagClickListenerTop;
    private TagView.OnTagClickListener tagClickListenerBottom;
    private String currentTag;

    @Override
    public String setTitle() {
        return "商闻.兴趣管理";
    }

    @Override
    public boolean setRightCompleteViewShow() {
        return true;
    }

    @Override
    public boolean setRightOverfloViewShow() {
        return false;
    }

    @Override
    public void onClickComplete() {
        Toast.makeText(this,"you clicked complete...",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickOverflow() {

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_fav_manager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tags_top = new ArrayList<String>();
        tags_bottom = new ArrayList<String>();
        initTags();
    }

    private void initTags() {
        String[] fav_tags_top = getResources().getStringArray(R.array.fav_tags_top);
        tags_top = Arrays.asList(fav_tags_top);

        String[] fav_tags_bottom = getResources().getStringArray(R.array.fav_tags_bootom);
        tags_bottom = Arrays.asList(fav_tags_bottom);
//        custom bg
        mTagContainerLayout.setTagBackgroundColor(Color.parseColor("#FFF7F7F7"));
        mTagContainerLayout2.setTagBackgroundColor(Color.parseColor("#FFF7F7F7"));
        tagClickListenerTop = new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                Log.d("TAG", "position=" + position);
                mTagContainerLayout.removeTag(position);
                mTagContainerLayout2.addTag(text);
            }

            @Override
            public void onTagLongClick(int position, String text) {

            }

            @Override
            public void onTagCrossClick(int position) {
                Timber.d("onTagCrossClick position="+position);
            }
        };

        tagClickListenerBottom = new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                mTagContainerLayout2.removeTag(position);
                mTagContainerLayout.addTag(text);
            }

            @Override
            public void onTagLongClick(int position, String text) {

            }

            @Override
            public void onTagCrossClick(int position) {
                Timber.d("onTagCrossClick position="+position);
            }
        };
        mTagContainerLayout.setOnTagClickListener(tagClickListenerTop);
        mTagContainerLayout2.setOnTagClickListener(tagClickListenerBottom);
        mTagContainerLayout.setTags(tags_top);
        mTagContainerLayout2.setTags(tags_bottom);
    }
}
