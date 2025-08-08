package com.wect.plants_frontend_android.Register;

import android.content.Intent;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
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
    private Button btnRegister;
    private EditText etAccount;
    private EditText etVerificationCode;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private CountDownTimer countDownTimer;
    private boolean isCounting = false;
    private Handler handler;
    private Runnable accountValidationRunnable;
    private Runnable codeValidationRunnable;
    private Runnable passwordValidationRunnable;
    private Runnable confirmPasswordValidationRunnable;
    private Runnable sendCodeRunnable; // 发送验证码防抖任务

    // Toast延迟显示时间常量
    private static final long TOAST_DELAY_TIME = 1000; // 1秒

    // Toast显示任务Runnable
    private Runnable accountToastRunnable;
    private Runnable codeToastRunnable;
    private Runnable passwordToastRunnable;
    private Runnable confirmPasswordToastRunnable;

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

        // 初始化控件
        initViews();

        // 设置监听器
        setListeners();


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
     * 初始化控件
     */
    private void initViews() {
        btnSendCode = findViewById(R.id.register_btn_send_code);
        btnRegister = findViewById(R.id.register_btn_register);
        etAccount = findViewById(R.id.register_account);
        etVerificationCode = findViewById(R.id.et_verification_code);
        etPassword = findViewById(R.id.register_password);
        etConfirmPassword = findViewById(R.id.register_confirm_password);

        // 初始化Handler（使用主线程Looper）
        handler = new Handler(Looper.getMainLooper());
    }

    /**
     * 设置监听器
     */
    private void setListeners() {
        // 账号输入框文本变化监听器 - 只更新按钮状态
        etAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // 取消前一个未执行的按钮状态更新
                handler.removeCallbacks(accountValidationRunnable);
                // 获取当前输入值
                String phone = s.toString();
                // 创建新的按钮状态更新任务
                accountValidationRunnable = () -> {
                    updateRegisterButtonStatus();
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
                if (!isValidPhoneNumber(phone) && !phone.isEmpty()) {
                    // 取消之前的Toast任务
                    handler.removeCallbacks(accountToastRunnable);
                    // 创建新的Toast任务
                    accountToastRunnable = () -> {
                        Toast.makeText(RegisterActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    };
                    // 延迟1秒执行
                    handler.postDelayed(accountToastRunnable, TOAST_DELAY_TIME);
                }
                updateRegisterButtonStatus();
            }
        });

        // 验证码输入框文本变化监听器 - 只更新按钮状态
        etVerificationCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // 取消前一个未执行的按钮状态更新
                handler.removeCallbacks(codeValidationRunnable);
                // 获取当前输入值
                String code = s.toString();
                // 创建新的按钮状态更新任务
                codeValidationRunnable = () -> {
                    updateRegisterButtonStatus();
                };
                // 延迟500ms执行，实现防抖
                handler.postDelayed(codeValidationRunnable, 500);
            }
        });

        // 验证码输入框失焦监听器
        etVerificationCode.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                // 取消前一个未执行的验证
                handler.removeCallbacks(codeValidationRunnable);
                // 获取当前输入值
                String code = etVerificationCode.getText().toString();
                // 立即执行验证（不再延迟）
                if (!isValidVerificationCode(code) && !code.isEmpty()) {
                    // 取消之前的Toast任务
                    handler.removeCallbacks(codeToastRunnable);
                    // 创建新的Toast任务
                    codeToastRunnable = () -> {
                        Toast.makeText(RegisterActivity.this, "验证码格式不正确", Toast.LENGTH_SHORT).show();
                    };
                    // 延迟1秒执行
                    handler.postDelayed(codeToastRunnable, TOAST_DELAY_TIME);
                }
                updateRegisterButtonStatus();
            }
        });

        // 密码输入框文本变化监听器 - 只更新按钮状态
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // 取消前一个未执行的按钮状态更新
                handler.removeCallbacks(passwordValidationRunnable);
                // 获取当前输入值
                String password = s.toString();
                // 创建新的按钮状态更新任务
                passwordValidationRunnable = () -> {
                    updateRegisterButtonStatus();
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
                if (!isValidPassword(password) && !password.isEmpty()) {
                    // 取消之前的Toast任务
                    handler.removeCallbacks(passwordToastRunnable);
                    // 创建新的Toast任务
                    passwordToastRunnable = () -> {
                        Toast.makeText(RegisterActivity.this, "密码长度不能少于6位", Toast.LENGTH_SHORT).show();
                    };
                    // 延迟1秒执行
                    handler.postDelayed(passwordToastRunnable, TOAST_DELAY_TIME);
                }
                updateRegisterButtonStatus();
            }
        });

        // 确认密码输入框文本变化监听器 - 只更新按钮状态
        etConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // 取消前一个未执行的按钮状态更新
                handler.removeCallbacks(confirmPasswordValidationRunnable);
                // 获取当前输入值
                String confirmPassword = s.toString();
                // 创建新的按钮状态更新任务
                confirmPasswordValidationRunnable = () -> {
                    updateRegisterButtonStatus();
                };
                // 延迟500ms执行，实现防抖
                handler.postDelayed(confirmPasswordValidationRunnable, 500);
            }
        });

        // 确认密码输入框失焦监听器
        etConfirmPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                // 取消前一个未执行的验证
                handler.removeCallbacks(confirmPasswordValidationRunnable);
                // 获取当前输入值
                String confirmPassword = etConfirmPassword.getText().toString();
                String password = etPassword.getText().toString();
                // 立即执行验证（不再延迟）
                if (!confirmPassword.equals(password) && !confirmPassword.isEmpty()) {
                    // 取消之前的Toast任务
                    handler.removeCallbacks(confirmPasswordToastRunnable);
                    // 创建新的Toast任务
                    confirmPasswordToastRunnable = () -> {
                        Toast.makeText(RegisterActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                    };
                    // 延迟1秒执行
                    handler.postDelayed(confirmPasswordToastRunnable, TOAST_DELAY_TIME);
                }
                updateRegisterButtonStatus();
            }
        });

        // 发送验证码按钮点击事件
        btnSendCode.setOnClickListener(v -> {
            // 取消前一个未执行的发送验证码任务
            handler.removeCallbacks(sendCodeRunnable);

            // 创建新的发送验证码任务
            sendCodeRunnable = () -> {
                String phone = etAccount.getText().toString();
                if (!isValidPhoneNumber(phone)) {
                    Toast.makeText(RegisterActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isCounting) {
                    startCountDown();
                    // 模拟发送验证码，实际项目中这里应该是请求服务器的代码
                    simulateSendVerificationCode();
                }
            };

            // 延迟300ms执行，实现防抖
            handler.postDelayed(sendCodeRunnable, 300);
        });

        // 注册按钮点击事件
        btnRegister.setOnClickListener(v -> {
            // 在这里实现注册逻辑
            String account = etAccount.getText().toString();
            String code = etVerificationCode.getText().toString();
            String password = etPassword.getText().toString();

            if (isValidPhoneNumber(account) && isValidVerificationCode(code) && isValidPassword(password) && password.equals(etConfirmPassword.getText().toString())) {
                // 注册逻辑
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                // 这里可以添加跳转到登录页面的逻辑
                finishWithAnimation();
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
     * 验证验证码格式
     */
    private boolean isValidVerificationCode(String code) {
        // 验证码为6位数字
        String regex = "^\\d{6}$";
        return code.matches(regex);
    }

    /**
     * 验证密码格式
     */
    private boolean isValidPassword(String password) {
        // 密码长度至少6位
        return password.length() >= 6;
    }

    /**
     * 更新注册按钮状态
     */
    private void updateRegisterButtonStatus() {
        String account = etAccount.getText().toString();
        String code = etVerificationCode.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        boolean isAccountValid = isValidPhoneNumber(account);
        boolean isCodeValid = isValidVerificationCode(code);
        boolean isPasswordValid = isValidPassword(password);
        boolean isConfirmPasswordValid = password.equals(confirmPassword) && !confirmPassword.isEmpty();

        btnRegister.setEnabled(isAccountValid && isCodeValid && isPasswordValid && isConfirmPasswordValid);
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
        // 清除所有未执行的任务，避免内存泄漏
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
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