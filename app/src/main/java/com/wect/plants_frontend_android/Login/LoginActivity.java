package com.wect.plants_frontend_android.Login;

import android.content.Intent;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.wect.plants_frontend_android.R;
import com.wect.plants_frontend_android.Register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    private boolean isFromRegister = false;
    private boolean isFromForgot = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);


        //实现模糊效果
        View greenBlurView = findViewById(R.id.login_bg_left);
        View gradientBlurView = findViewById(R.id.login_bg_right);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // 对左层应用模糊
            greenBlurView.setRenderEffect(
                    RenderEffect.createBlurEffect(300f, 300f, Shader.TileMode.CLAMP)
            );

            // 对右层应用模糊
            gradientBlurView.setRenderEffect(
                    RenderEffect.createBlurEffect(300f, 300f, Shader.TileMode.CLAMP)
            );
        }

        //去注册按钮
        Button loginToRegister = findViewById(R.id.login_to_register);
        loginToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册用户界面
                goToRegister(v);
            }
        });

        //去注册按钮
        TextView loginForgotPassword = findViewById(R.id.login_forgot_password);
        loginForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册用户界面
                goToForgot(v);
            }
        });

        // 处理返回导航 (替代已弃用的onBackPressed)
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // 默认行为是finish()
                finish();
                // 根据来源决定返回动画方向
                if (isFromRegister) {
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                } else if (isFromForgot) {
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });
    }

    /**
     * 跳转注册动画
     * @param view
     */
    public void goToRegister(View view) {
        isFromRegister = true;
        isFromForgot = false;
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * 跳转忘记密码
     * @param view
     */
    public void goToForgot(View view) {
        isFromRegister = false;
        isFromForgot = true;
        Intent intent = new Intent(this, ForgotActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 重置标志
        isFromRegister = false;
        isFromForgot = false;
    }

}