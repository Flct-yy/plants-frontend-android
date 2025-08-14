package com.wect.plants_frontend_android.Ui.Fragments.User.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.wect.plants_frontend_android.R;

public class UserEditFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 先加载布局
        View view = inflater.inflate(R.layout.fragment_user_edit, container, false);

        // 找到控件
        EditText editContent = view.findViewById(R.id.user_edit_content);
        TextView editCounter = view.findViewById(R.id.user_edit_counter);

        // 监听文字变化 (56/200)
        editContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                int length = s != null ? s.length() : 0;
                editCounter.setText(length + "/200");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });


        // 点击按钮返回
        View backBtn = view.findViewById(R.id.edit_back);
        backBtn.setOnClickListener(v -> {
            // 返回到上一个Fragment（即UserFragment）
            getParentFragmentManager().popBackStack();
        });

        return view;
    }
}
