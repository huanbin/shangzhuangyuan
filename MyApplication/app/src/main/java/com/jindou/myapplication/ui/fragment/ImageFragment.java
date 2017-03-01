package com.jindou.myapplication.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jindou.myapplication.R;

/**
 * Created by zhishi on 2017/3/1.
 */

public class ImageFragment extends Fragment {

    private static final String IMAGE_URL = "url";
    private static final String CONTEXT = "context";
    private String url;
    private AppCompatActivity activity;

    public static ImageFragment newInstance(Context context, String url) {
        ImageFragment fragment = new ImageFragment();
        Bundle bundle=new Bundle();
        bundle.putString(IMAGE_URL,url);
        fragment.setArguments(bundle);
        return fragment;
    }

    public ImageFragment() {
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        url = (String) arguments.get(IMAGE_URL);
        activity = (AppCompatActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scan_picture_dialog_view, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.ivShowPicture);
//        imageView.setOnClickListener();
        Glide.with(activity).load(url).crossFade().into(imageView);
        return view;
    }

}
