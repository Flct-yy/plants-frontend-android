package com.wect.plants_frontend_android.Fragments.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wect.plants_frontend_android.Adapter.NormalItemsAdapter;
import com.wect.plants_frontend_android.Model.NormalItem;
import com.wect.plants_frontend_android.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerFeatured;
    private NormalItemsAdapter adapter;
    private List<NormalItem> featuredItems;

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
        featuredItems = new ArrayList<>();
        featuredItems.add(new NormalItem("DourDarcel", "10K 观看 • 4.93K 收藏", R.drawable.logo));
        featuredItems.add(new NormalItem("CyberBrokersDeployer", "8K 观看 • 3.5K 收藏", R.drawable.logo));
        featuredItems.add(new NormalItem("BoredApeYachtClub", "12K 观看 • 5K 收藏", R.drawable.logo));
        featuredItems.add(new NormalItem("Azuki", "7K 观看 • 2.9K 收藏", R.drawable.logo));
        featuredItems.add(new NormalItem("DourDarcel", "10K 观看 • 4.93K 收藏", R.drawable.logo));
        featuredItems.add(new NormalItem("CyberBrokersDeployer", "8K 观看 • 3.5K 收藏", R.drawable.logo));
        featuredItems.add(new NormalItem("BoredApeYachtClub", "12K 观看 • 5K 收藏", R.drawable.logo));
        featuredItems.add(new NormalItem("Azuki", "7K 观看 • 2.9K 收藏", R.drawable.logo));
        featuredItems.add(new NormalItem("DourDarcel", "10K 观看 • 4.93K 收藏", R.drawable.logo));
        featuredItems.add(new NormalItem("CyberBrokersDeployer", "8K 观看 • 3.5K 收藏", R.drawable.logo));
        featuredItems.add(new NormalItem("BoredApeYachtClub", "12K 观看 • 5K 收藏", R.drawable.logo));
        featuredItems.add(new NormalItem("Azuki", "7K 观看 • 2.9K 收藏", R.drawable.logo));

        // 设置网格布局为 2 列
        recyclerFeatured.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // 设置适配器
        adapter = new NormalItemsAdapter(getContext(), featuredItems);
        recyclerFeatured.setAdapter(adapter);

        return view;
    }
}