package com.wect.plants_frontend_android;

import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wect.plants_frontend_android.Based.BaseActivity;
import com.wect.plants_frontend_android.Fragments.Chart.ChartFragment;
import com.wect.plants_frontend_android.Fragments.Home.HomeFragment;
import com.wect.plants_frontend_android.Fragments.PlantList.PlantListFragment;
import com.wect.plants_frontend_android.Fragments.Settings.SettingsFragment;

public class MainActivity extends BaseActivity {

    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 不需要额外写 Insets 代码


        //判断是否登录 是否是新用户

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
                loadFragment(new ChartFragment());
            } else if (id == R.id.nav_settings) {
                loadFragment(new SettingsFragment());
            }
            return true;
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