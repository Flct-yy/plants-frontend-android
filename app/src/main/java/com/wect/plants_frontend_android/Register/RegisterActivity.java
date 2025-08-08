package com.wect.plants_frontend_android.Register;

import android.content.Intent;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.wect.plants_frontend_android.Login.LoginActivity;
import com.wect.plants_frontend_android.R;

public class RegisterActivity extends AppCompatActivity {
    private Button btnSendCode;
    private EditText etVerificationCode;
    private CountDownTimer countDownTimer;
    private boolean isCounting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

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

        //验证码
        btnSendCode = findViewById(R.id.register_btn_send_code);
        etVerificationCode = findViewById(R.id.et_verification_code);

        // 发送验证码按钮点击事件
        btnSendCode.setOnClickListener(v -> {
            if (!isCounting) {
                startCountDown();
                // 模拟发送验证码，实际项目中这里应该是请求服务器的代码
                simulateSendVerificationCode();
            }
        });

        //去登录按钮
        Button registerToLogin = findViewById(R.id.register_to_login);
        registerToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册用户界面
                finishWithAnimation();
            }
        });

        // 现代方式处理返回按钮 (API 33+推荐)
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finishWithAnimation();
            }
        });

    }

    /**
     * 开始60秒倒计时
     */
    private void startCountDown() {
        isCounting = true;
        btnSendCode.setEnabled(false);

        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btnSendCode.setText(millisUntilFinished / 1000 + "s后重发");
            }

            @Override
            public void onFinish() {
                isCounting = false;
                btnSendCode.setEnabled(true);
                btnSendCode.setText("发送验证码");
            }
        }.start();
    }
    /**
     * 模拟发送验证码（实际项目中替换为网络请求）
     */
    private void simulateSendVerificationCode() {
        btnSendCode.postDelayed(() -> {
            etVerificationCode.setHint("123456");
            Toast.makeText(this, "验证码已发送", Toast.LENGTH_SHORT).show();
        }, 3000);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    /**
     * 返回登录页面（带左进右出动画）
     */
    private void finishWithAnimation() {
        finish(); // 直接结束当前Activity，会触发下面的finish()方法中的动画
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
    @Override
    public void finish() {
        super.finish();
        // 确保在直接调用finish()时也有动画
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


}