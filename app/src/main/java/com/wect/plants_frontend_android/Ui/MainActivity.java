package com.wect.plants_frontend_android.Ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wect.plants_frontend_android.Ui.Based.BaseActivity;
import com.wect.plants_frontend_android.Ui.Fragments.ChatAI.AIFragment;
import com.wect.plants_frontend_android.Ui.Fragments.Message.MessageFragment;
import com.wect.plants_frontend_android.Ui.Fragments.Home.HomeFragment;
import com.wect.plants_frontend_android.Ui.Fragments.PlantList.PlantListFragment;
import com.wect.plants_frontend_android.Ui.Fragments.User.UserFragment;
import com.wect.plants_frontend_android.R;

public class MainActivity extends BaseActivity {

    // 地下导航
    private BottomNavigationView bottomNavigationView;
    // 中间的按钮
    private FloatingActionButton fabCenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 不需要额外写 Insets 代码


        //判断是否登录 是否是新用户
        // 导航栏动画淡入淡出 添加
        // 滑动切换界面
        // 导航栏指示器

        // 初始化控件
        initViews();

        // 设置监听器
        setListeners();

        // 默认显示首页
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

    }

    /**
     * 初始化控件
     */
    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        fabCenter = findViewById(R.id.fab_center);
    }
    /**
     * 设置监听器
     */
    private void setListeners() {

        // 底部导航点击
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            // 页面切换
            if (id == R.id.nav_home) {
                loadFragment(new HomeFragment());
            } else if (id == R.id.nav_list) {
                loadFragment(new PlantListFragment());
            } else if (id == R.id.nav_chart) {
                loadFragment(new MessageFragment());
            } else if (id == R.id.nav_settings) {
                loadFragment(new UserFragment());
            }
            return true;
        });

        // 中间悬浮按钮点击（打开 AIFragment）
        fabCenter.setOnClickListener(v -> {
            loadFragment(new AIFragment());
            // 如果需要显示它在导航选中状态，可以手动取消导航栏选中项
            // 分组 ID   0，表示作用于所有菜单项。
            bottomNavigationView.getMenu().setGroupCheckable(0, true, false);
            for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
                bottomNavigationView.getMenu().getItem(i).setChecked(false);
            }
            bottomNavigationView.getMenu().setGroupCheckable(0, true, true);
        });
    }

    /**
     * 改变Fragment方法
     * @param fragment
     */
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }


}