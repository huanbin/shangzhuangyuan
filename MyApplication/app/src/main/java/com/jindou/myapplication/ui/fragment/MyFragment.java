package com.jindou.myapplication.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.jindou.myapplication.R;

/**
 * Created by zhishi on 2017/2/17.
 */

public class MyFragment extends Fragment {
    private static String KEY_MSG="btMsg";
    private String btMsg;
    public MyFragment() {
    }

    public static MyFragment newInstance(String msg){
       MyFragment fragment=new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_MSG,msg);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btMsg= getArguments().getString(KEY_MSG);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shangwen_item, container, false);
        return view;
    }
}
