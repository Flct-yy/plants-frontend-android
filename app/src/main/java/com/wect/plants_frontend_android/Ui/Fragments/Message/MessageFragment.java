package com.wect.plants_frontend_android.Ui.Fragments.Message;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wect.plants_frontend_android.R;
import com.wect.plants_frontend_android.Utils.BarUtils;


public class MessageFragment extends Fragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        // 适应状态栏的padding
        BarUtils.applyStatusBarPadding(view);


        return view;
    }
}