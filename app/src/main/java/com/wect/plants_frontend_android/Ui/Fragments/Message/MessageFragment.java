package com.wect.plants_frontend_android.Ui.Fragments.Message;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wect.plants_frontend_android.R;
import com.wect.plants_frontend_android.Ui.Adapter.MessageItemAdapter;
import com.wect.plants_frontend_android.Ui.Adapter.PlantCardAdapter;
import com.wect.plants_frontend_android.Utils.BarUtils;
import com.wect.plants_frontend_android.Viewmodel.MessageViewModel;
import com.wect.plants_frontend_android.Viewmodel.PlantListViewModel;

import java.util.ArrayList;


public class MessageFragment extends Fragment {

    private MessageViewModel viewModel;
    private MessageItemAdapter adapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MessageViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        // 适应状态栏的padding
        BarUtils.applyStatusBarPadding(view);


        // 初始化RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MessageItemAdapter(new ArrayList<>()); // 初始空数据
        recyclerView.setAdapter(adapter);

        // 观察数据变化
        viewModel.getMessages().observe(getViewLifecycleOwner(), messages -> {
            adapter.setMessageItem(messages);
        });


        return view;
    }
}