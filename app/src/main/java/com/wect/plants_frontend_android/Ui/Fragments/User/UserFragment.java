package com.wect.plants_frontend_android.Ui.Fragments.User;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.wect.plants_frontend_android.R;

public class UserFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // 先加载布局
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 这里才能安全 findViewById
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);

        // 设置适配器（这里传 getActivity() 或 this，根据 MyPagerAdapter 构造方法）
        viewPager.setAdapter(new UserTabAdapter(getActivity()));

        // TabLayout 和 ViewPager2 联动
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            String[] titles = {"作品", "推荐", "收藏", "喜欢"};
            tab.setText(titles[position]);
        }).attach();
    }
}
