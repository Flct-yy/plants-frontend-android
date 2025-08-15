package com.wect.plants_frontend_android.Ui.Fragments.User;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.wect.plants_frontend_android.R;
import com.wect.plants_frontend_android.Ui.Fragments.User.Fragments.UserEditFragment;

public class UserFragment extends Fragment {

    private Button toEdit;

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
            String[] titles = {"作品", "喜欢"};
            tab.setText(titles[position]);
        }).attach();

        // 初始化控件
        initViews();

        // 设置监听器
        setListeners();
    }

    private void initViews() {
        toEdit = requireView().findViewById(R.id.user_edit); // 找到按钮
    }
    /**
     * 设置监听器
     */
    private void setListeners() {
        toEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.anim.slide_in_right,  // 新Fragment进入动画
                                R.anim.slide_out_left,  // 当前Fragment退出动画
                                R.anim.slide_in_left,   // 返回时新Fragment进入
                                R.anim.slide_out_right  // 返回时当前Fragment退出
                        )
                        .replace(R.id.container, new UserEditFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
