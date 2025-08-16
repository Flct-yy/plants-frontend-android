package com.wect.plants_frontend_android.Ui.Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;

import com.jakewharton.rxbinding4.view.RxView;
import com.wect.plants_frontend_android.Data.local.PreferencesManager;
import com.wect.plants_frontend_android.R;
import com.wect.plants_frontend_android.Ui.MainActivity;
import com.wect.plants_frontend_android.Ui.Register.RegisterActivity;
import com.wect.plants_frontend_android.Utils.BarUtils;
import com.wect.plants_frontend_android.Utils.LoginUiUtils;
import com.wect.plants_frontend_android.Utils.ToastUtil;
import com.wect.plants_frontend_android.Ui.Based.BaseActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class LoginActivity extends BaseActivity {

    private boolean isFromRegister = false;
    private boolean isFromForgot = false;

    // 输入框和按钮引用
    private EditText etAccount;
    private EditText etPassword;
    private Button btnLogin;
    private Button loginToRegister;
    private TextView loginForgotPassword;
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
        setContentView(R.layout.activity_login);


        // 初始化控件
        initViews();

        // 设置监听器
        setListeners();

        //实现模糊效果
        View leftBlurView = findViewById(R.id.login_bg_left);
        View rightBlurView = findViewById(R.id.login_bg_right);
        LoginUiUtils.LoginBackgroundBlur(leftBlurView, rightBlurView);

        //设置登录按钮防抖点击
        setupLoginButtonDebounce();

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

        // 注册按钮
        loginToRegister = findViewById(R.id.login_to_register);
        // 忘记密码按钮
        loginForgotPassword = findViewById(R.id.login_forgot_password);
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

        // 账号输入框失焦监听器
        etAccount.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                // 取消前一个未执行的验证
                handler.removeCallbacks(accountValidationRunnable);
                // 获取当前输入值
                String phone = etAccount.getText().toString();
                // 立即执行验证（不再延迟）
                if (!LoginUiUtils.isValidPhoneNumber(phone) && !phone.isEmpty()) {
                    // 取消之前的Toast任务
                    handler.removeCallbacks(accountToastRunnable);
                    // 创建新的Toast任务
                    accountToastRunnable = () -> {
                        ToastUtil.show(LoginActivity.this, "请输入正确的手机号");
                    };
                    // 延迟1秒执行
                    handler.postDelayed(accountToastRunnable, TOAST_DELAY_TIME);
                }
                updateLoginButtonStatus();
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

        // 密码输入框失焦监听器
        etPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                // 取消前一个未执行的验证
                handler.removeCallbacks(passwordValidationRunnable);
                // 获取当前输入值
                String password = etPassword.getText().toString();
                // 立即执行验证（不再延迟）
                if (!LoginUiUtils.isValidPassword(password) && !password.isEmpty()) {
                    // 取消之前的Toast任务
                    handler.removeCallbacks(passwordToastRunnable);
                    // 创建新的Toast任务
                    passwordToastRunnable = () -> {
                        ToastUtil.show(LoginActivity.this, "密码长度至少6位");
                    };
                    // 延迟1秒执行
                    handler.postDelayed(passwordToastRunnable, TOAST_DELAY_TIME);
                }
                updateLoginButtonStatus();
            }
        });

        // 注册按钮按钮跳转
        loginToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册用户界面
                goToRegister(v);
            }
        });

        // 忘记密码按钮跳转
        loginForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到忘记密码界面
                goToForgot(v);
            }
        });
    }

    /**
     * 更新登录按钮状态
     */
    private void updateLoginButtonStatus() {
        String account = etAccount.getText().toString();
        String password = etPassword.getText().toString();
        boolean isValid = LoginUiUtils.isValidPhoneNumber(account) && LoginUiUtils.isValidPassword(password);
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

                            if (LoginUiUtils.isValidPhoneNumber(account) && LoginUiUtils.isValidPassword(password)) {
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
            // 假设后端返回了 userId 和 token
            // 没做
            long userId = 001;
            String token = "abcdefg123456"; // 这里应该是从接口返回的 token

            // 保存到 DataStore
            PreferencesManager.saveString(this, PreferencesManager.USER_TOKEN_KEY, token);
            PreferencesManager.saveLong(this, PreferencesManager.USER_ID_KEY, userId);
            PreferencesManager.saveLong(this, PreferencesManager.LAST_LOGIN_KEY, System.currentTimeMillis());

            ToastUtil.show(this, "登录成功");

            // 恢复按钮状态
            btnLogin.setEnabled(true);
            btnLogin.setAlpha(1f);

            // 跳转到主界面
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

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