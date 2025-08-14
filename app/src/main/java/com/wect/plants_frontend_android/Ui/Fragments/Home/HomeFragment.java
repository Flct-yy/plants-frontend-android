package com.wect.plants_frontend_android.Ui.Fragments.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wect.plants_frontend_android.Ui.Adapter.NormalItemsAdapter;
import com.wect.plants_frontend_android.Data.Model.NormalArticle;
import com.wect.plants_frontend_android.R;
import com.wect.plants_frontend_android.Utils.BarUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerFeatured;
    private NormalItemsAdapter adapter;
    private List<NormalArticle> articleList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // 绑定布局
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // 找 RecyclerView
        recyclerFeatured = view.findViewById(R.id.recyclerNormal);

        // 准备数据
        articleList = new ArrayList<>();
        articleList.add(new NormalArticle(
                "小王",
                "如何养好绿萝",
                "08-11",
                false,
                R.drawable.ic_user,
                R.drawable.logo,
                10234,
                456,
                789
        ));
        articleList.add(new NormalArticle(
                "小李",
                "多肉植物的养护技巧",
                "08-10",
                true,
                R.drawable.logo,
                R.drawable.logo,
                5689,
                123,
                456
        ));
        articleList.add(new NormalArticle(
                "小张",
                "空气凤梨怎么养？",
                "08-09",
                false,
                R.drawable.ic_user,
                R.drawable.ic_user,
                3021,
                78,
                213
        ));


        // 设置网格布局为 1 列
        recyclerFeatured.setLayoutManager(new GridLayoutManager(getContext(), 1));

        // 设置适配器
        adapter = new NormalItemsAdapter(getContext(), articleList);
        recyclerFeatured.setAdapter(adapter);

        // 适应状态栏的padding
        BarUtils.applyStatusBarPadding(view);

        return view;
    }
}