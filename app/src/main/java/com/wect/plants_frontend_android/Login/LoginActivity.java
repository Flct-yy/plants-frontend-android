package com.wect.plants_frontend_android.Login;

import android.content.Intent;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.jakewharton.rxbinding4.view.RxView;
import com.wect.plants_frontend_android.R;
import com.wect.plants_frontend_android.Register.RegisterActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class LoginActivity extends AppCompatActivity {

    private boolean isFromRegister = false;
    private boolean isFromForgot = false;

    // 输入框和按钮引用
    private EditText etAccount;
    private EditText etPassword;
    private Button btnLogin;
    private Handler handler;
    private Runnable accountValidationRunnable;
    private Runnable passwordValidationRunnable;

    // Toast延迟显示时间常量
    private static final long TOAST_DELAY_TIME = 1000; // 1秒

    // Toast显示任务Runnable
    private Runnable accountToastRunnable;
    private Runnable passwordToastRunnable;

    private final CompositeDisposable disposables = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // 初始化控件
        initViews();

        // 设置监听器
        setListeners();

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

        // 账号输入框失焦监听器
        etAccount.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                // 取消前一个未执行的验证
                handler.removeCallbacks(accountValidationRunnable);
                // 获取当前输入值
                String phone = etAccount.getText().toString();
                // 立即执行验证（不再延迟）
                if (!isValidPhoneNumber(phone) && !phone.isEmpty()) {
                    // 取消之前的Toast任务
                    handler.removeCallbacks(accountToastRunnable);
                    // 创建新的Toast任务
                    accountToastRunnable = () -> {
                        Toast.makeText(LoginActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    };
                    // 延迟1秒执行
                    handler.postDelayed(accountToastRunnable, TOAST_DELAY_TIME);
                }
                updateLoginButtonStatus();
            }
        });

        // 密码输入框失焦监听器
        etPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                // 取消前一个未执行的验证
                handler.removeCallbacks(passwordValidationRunnable);
                // 获取当前输入值
                String password = etPassword.getText().toString();
                // 立即执行验证（不再延迟）
                if (!isValidPassword(password) && !password.isEmpty()) {
                    // 取消之前的Toast任务
                    handler.removeCallbacks(passwordToastRunnable);
                    // 创建新的Toast任务
                    passwordToastRunnable = () -> {
                        Toast.makeText(LoginActivity.this, "密码长度至少6位", Toast.LENGTH_SHORT).show();
                    };
                    // 延迟1秒执行
                    handler.postDelayed(passwordToastRunnable, TOAST_DELAY_TIME);
                }
                updateLoginButtonStatus();
            }
        });

        //设置登录按钮防抖点击
        setupLoginButtonDebounce();

        //去注册按钮
        Button loginToRegister = findViewById(R.id.login_to_register);
        loginToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册用户界面
                goToRegister(v);
            }
        });

        //去忘记密码按钮
        TextView loginForgotPassword = findViewById(R.id.login_forgot_password);
        loginForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到忘记密码界面
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
     * 初始化控件
     */
    private void initViews() {
        etAccount = findViewById(R.id.login_account);
        etPassword = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.login_btn_login);

        // 初始化Handler（使用主线程Looper）
        handler = new Handler(Looper.getMainLooper());
    }

    /**
     * 设置监听器
     */
    private void setListeners() {
        // 账号输入框文本变化监听器
        etAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // 取消前一个未执行的按钮状态更新
                handler.removeCallbacks(accountValidationRunnable);
                // 创建新的按钮状态更新任务
                accountValidationRunnable = () -> {
                    updateLoginButtonStatus();
                };
                // 延迟500ms执行，实现防抖
                handler.postDelayed(accountValidationRunnable, 500);
            }
        });

        // 密码输入框文本变化监听器
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // 取消前一个未执行的按钮状态更新
                handler.removeCallbacks(passwordValidationRunnable);
                // 创建新的按钮状态更新任务
                passwordValidationRunnable = () -> {
                    updateLoginButtonStatus();
                };
                // 延迟500ms执行，实现防抖
                handler.postDelayed(passwordValidationRunnable, 500);
            }
        });
    }

    /**
     * 验证手机号格式
     */
    private boolean isValidPhoneNumber(String phone) {
        // 简单的手机号验证正则表达式（中国手机号）
        String regex = "^1[3-9]\\d{9}$";
        return phone.matches(regex);
    }

    /**
     * 验证密码格式
     */
    private boolean isValidPassword(String password) {
        // 密码长度至少6位
        return password.length() >= 6;
    }

    /**
     * 更新登录按钮状态
     */
    private void updateLoginButtonStatus() {
        String account = etAccount.getText().toString();
        String password = etPassword.getText().toString();
        boolean isValid = isValidPhoneNumber(account) && isValidPassword(password);
        btnLogin.setEnabled(isValid);
    }

    /**
     * 设置登录按钮防抖点击
     */
    private void setupLoginButtonDebounce() {
        disposables.add(
                RxView.clicks(btnLogin)
                        .throttleFirst(1, TimeUnit.SECONDS) // 1秒内只响应第一次点击
                        .subscribe(unit -> {
                            String account = etAccount.getText().toString();
                            String password = etPassword.getText().toString();

                            if (isValidPhoneNumber(account) && isValidPassword(password)) {
                                // 禁用按钮防止重复点击
                                btnLogin.setEnabled(false);
                                btnLogin.setAlpha(0.5f);

                                // 执行登录逻辑
                                performLogin(account, password);
                            }
                        })
        );
    }

    /**
     * 登录逻辑
     * @param account
     * @param password
     */
    private void performLogin(String account, String password) {
        // 模拟网络请求延迟
        handler.postDelayed(() -> {
            // 这里应该是实际的登录逻辑
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();

            // 恢复按钮状态
            btnLogin.setEnabled(true);
            btnLogin.setAlpha(1f);

            // 可以在这里添加页面跳转逻辑
        }, 1500);
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

    @Override
    protected void onDestroy() {
        disposables.clear();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }

}