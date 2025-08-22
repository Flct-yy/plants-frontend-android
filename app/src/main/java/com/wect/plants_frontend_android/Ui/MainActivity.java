package com.wect.plants_frontend_android.Ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wect.plants_frontend_android.Data.local.PreferencesManager;
import com.wect.plants_frontend_android.Ui.Activity.Based.BaseActivity;
import com.wect.plants_frontend_android.Ui.Activity.EditArticles.EditArticlesActivity;
import com.wect.plants_frontend_android.Ui.Fragments.Message.MessageFragment;
import com.wect.plants_frontend_android.Ui.Fragments.Home.HomeFragment;
import com.wect.plants_frontend_android.Ui.Fragments.PlantList.PlantListFragment;
import com.wect.plants_frontend_android.Ui.Fragments.User.UserFragment;
import com.wect.plants_frontend_android.R;
import com.wect.plants_frontend_android.Ui.Activity.Login.LoginActivity;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    // 地下导航
    private BottomNavigationView bottomNavigationView;
    // 中间的按钮
    private FloatingActionButton fabCenter;

    private final CompositeDisposable disposables = new CompositeDisposable();
    private boolean isCheckingLogin = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 设置一个加载中的临时布局
//        setContentView(R.layout.activity_loading);

        //判断是否登录 是否是新用户
        isUserLogin(); // 先检查登录状态

        // 导航栏动画淡入淡出 添加
        // 滑动切换界面
        // 导航栏指示器

    }

    private void isUserLogin() {
        if (isCheckingLogin) return;
        isCheckingLogin = true;

        // 先检查本地 token
        disposables.add(
            PreferencesManager.getString(this, PreferencesManager.USER_TOKEN_KEY, "")
                .firstOrError() // 更严格的单次获取
                .subscribeOn(Schedulers.io())// 明确指定 I/O 线程执行读取操作
                .observeOn(AndroidSchedulers.mainThread()) // 回到主线程 确保在主线程释放资源
                .subscribe(token -> {
                    isCheckingLogin = false;
                    if (isFinishing() || isDestroyed()) return; // 增加 isDestroyed 检查

                    if (token.isEmpty()) {
                        // 没有登录信息，跳转到登录界面
                        navigateToLogin();
                    } else {
                        // 已登录，加载主界面
                        setupMainUI(); // 提取UI初始化逻辑
                    }
                }, throwable -> {
                    // 读取异常（可以当作未登录处理）

                    isCheckingLogin = false;
                    if (!isFinishing()) {
                        navigateToLogin();
                    }
                })
        );
    }

    /**
     * 跳转login界面
     */
    private void navigateToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    /**
     * 加载主界面
     */
    private void setupMainUI() {
        // 设置导航栏的边距
        setContentView(R.layout.activity_main);

        // 初始化控件
        initViews();

        // 设置监听器
        setListeners();

        // 设置默认选中项 默认显示首页
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }

    @Override
    protected void onDestroy() {
        disposables.clear(); // 统一清理所有订阅
        super.onDestroy();
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

        // 中间悬浮按钮点击（打开 ）
        fabCenter.setOnClickListener(v -> {

            // 如果需要显示它在导航选中状态，可以手动取消导航栏选中项
            // 分组 ID   0，表示作用于所有菜单项。
            bottomNavigationView.getMenu().setGroupCheckable(0, true, false);
            for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
                bottomNavigationView.getMenu().getItem(i).setChecked(false);
            }
            bottomNavigationView.getMenu().setGroupCheckable(0, true, true);

            // 跳转到 EditArticlesActivity
            Intent intent = new Intent(this, EditArticlesActivity.class);
            startActivity(intent);
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