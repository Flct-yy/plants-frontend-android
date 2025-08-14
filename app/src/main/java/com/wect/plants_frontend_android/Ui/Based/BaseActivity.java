package com.wect.plants_frontend_android.Ui.Based;

import android.os.Bundle;
import android.view.View;
import android.view.WindowInsetsController;

import androidx.activity.EdgeToEdge;
import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // 开启沉浸式
        setSystemBarColors(getSystemBarColorRes()); // 设置系统栏颜色
    }

    /**
     * 设置状态栏 & 导航栏颜色
     */
    protected void setSystemBarColors(@ColorRes int colorRes) {
        int color = ContextCompat.getColor(this, colorRes);
        getWindow().setStatusBarColor(color);
        getWindow().setNavigationBarColor(color);

        // 设置状态栏图标颜色
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            getWindow().getInsetsController().setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            );
        } else {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // 状态栏深色图标
            );
        }
    }

    /**
     * 子类可以重写这个方法，修改系统栏颜色
     */
    protected int getSystemBarColorRes() {
        return android.R.color.transparent;

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        // 自动适配系统栏内边距
//        View rootView = findViewById(android.R.id.content);
//        if (rootView != null) {
//            ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
//                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//                return insets;
//            });
//        }


    }
}
