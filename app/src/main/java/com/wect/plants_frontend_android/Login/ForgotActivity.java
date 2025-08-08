package com.wect.plants_frontend_android.Login;

import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
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

import com.wect.plants_frontend_android.R;

public class ForgotActivity extends AppCompatActivity {

    private Button btnSendCode;
    private Button btnResetPassword;
    private EditText etVerificationCode;
    private EditText etAccount; // 假设存在账号输入框
    private EditText etNewPassword;
    private EditText etConfirmNewPassword;
    private CountDownTimer countDownTimer;
    private boolean isCounting = false;
    private Handler handler;
    private Runnable verificationCodeValidationRunnable;
    private Runnable verificationCodeToastRunnable;
    private Runnable accountValidationRunnable;
    private Runnable accountToastRunnable;
    private Runnable newPasswordValidationRunnable;
    private Runnable newPasswordToastRunnable;
    private Runnable confirmNewPasswordValidationRunnable;
    private Runnable confirmNewPasswordToastRunnable;
    private Runnable sendCodeRunnable; // 发送验证码防抖任务

    // Toast延迟显示时间常量
    private static final long TOAST_DELAY_TIME = 1000; // 1秒

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);

        // 初始化handler
        handler = new Handler();

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

        //去登录按钮
        Button registerToLogin = findViewById(R.id.register_to_login);
        registerToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册用户界面
                finishWithAnimation();
            }
        });

        // 账号输入框（假设布局中有此控件）
        etAccount = findViewById(R.id.forgot_account);

        // 验证码
        btnSendCode = findViewById(R.id.forogt_btn_send_code);
        etVerificationCode = findViewById(R.id.et_verification_code);

        // 新密码和确认密码
        etNewPassword = findViewById(R.id.forogt_password);
        etConfirmNewPassword = findViewById(R.id.forgot_confirm_password);
        btnResetPassword = findViewById(R.id.forgot_btn_forgot);

        // 新密码输入框文本变化监听器 - 只更新按钮状态
        etNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // 取消前一个未执行的按钮状态更新
                handler.removeCallbacks(newPasswordValidationRunnable);
                // 获取当前输入值
                String password = s.toString();
                // 创建新的按钮状态更新任务
                newPasswordValidationRunnable = () -> {
                    updateButtonStatus();
                };
                // 延迟500ms执行，实现防抖
                handler.postDelayed(newPasswordValidationRunnable, 500);
            }
        });

        // 新密码输入框失焦监听器
        etNewPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                // 取消前一个未执行的验证
                handler.removeCallbacks(newPasswordValidationRunnable);
                // 获取当前输入值
                String password = etNewPassword.getText().toString();
                // 立即执行验证
                if (!isValidPassword(password) && !password.isEmpty()) {
                    // 取消之前的Toast任务
                    handler.removeCallbacks(newPasswordToastRunnable);
                    // 创建新的Toast任务
                    newPasswordToastRunnable = () -> {
                        Toast.makeText(ForgotActivity.this, "密码长度不能少于6位", Toast.LENGTH_SHORT).show();
                    };
                    // 延迟1秒执行
                    handler.postDelayed(newPasswordToastRunnable, TOAST_DELAY_TIME);
                }
                updateButtonStatus();
            }
        });

        // 确认新密码输入框文本变化监听器 - 只更新按钮状态
        etConfirmNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // 取消前一个未执行的按钮状态更新
                handler.removeCallbacks(confirmNewPasswordValidationRunnable);
                // 获取当前输入值
                String confirmPassword = s.toString();
                // 创建新的按钮状态更新任务
                confirmNewPasswordValidationRunnable = () -> {
                    updateButtonStatus();
                };
                // 延迟500ms执行，实现防抖
                handler.postDelayed(confirmNewPasswordValidationRunnable, 500);
            }
        });

        // 确认新密码输入框失焦监听器
        etConfirmNewPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                // 取消前一个未执行的验证
                handler.removeCallbacks(confirmNewPasswordValidationRunnable);
                // 获取当前输入值
                String confirmPassword = etConfirmNewPassword.getText().toString();
                String password = etNewPassword.getText().toString();
                // 立即执行验证
                if (!confirmPassword.equals(password) && !confirmPassword.isEmpty()) {
                    // 取消之前的Toast任务
                    handler.removeCallbacks(confirmNewPasswordToastRunnable);
                    // 创建新的Toast任务
                    confirmNewPasswordToastRunnable = () -> {
                        Toast.makeText(ForgotActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                    };
                    // 延迟1秒执行
                    handler.postDelayed(confirmNewPasswordToastRunnable, TOAST_DELAY_TIME);
                }
                updateButtonStatus();
            }
        });

        // 重置密码按钮点击事件
        btnResetPassword.setOnClickListener(v -> {
            // 在这里实现重置密码的逻辑
            // 通常需要验证所有输入项都有效
            if (isFormValid()) {
                // 执行重置密码的网络请求等操作
                Toast.makeText(ForgotActivity.this, "密码重置成功", Toast.LENGTH_SHORT).show();
                finishWithAnimation();
            }
        });

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
                String account = s.toString();
                // 创建新的按钮状态更新任务
                accountValidationRunnable = () -> {
                    updateButtonStatus();
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
                String account = etAccount.getText().toString();
                // 立即执行验证
                if (!isValidAccount(account) && !account.isEmpty()) {
                    // 取消之前的Toast任务
                    handler.removeCallbacks(accountToastRunnable);
                    // 创建新的Toast任务
                    accountToastRunnable = () -> {
                        Toast.makeText(ForgotActivity.this, "请输入正确的账号", Toast.LENGTH_SHORT).show();
                    };
                    // 延迟1秒执行
                    handler.postDelayed(accountToastRunnable, TOAST_DELAY_TIME);
                }
                updateButtonStatus();
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
                handler.removeCallbacks(verificationCodeValidationRunnable);
                // 获取当前输入值
                String code = s.toString();
                // 创建新的按钮状态更新任务
                verificationCodeValidationRunnable = () -> {
                    updateButtonStatus();
                };
                // 延迟500ms执行，实现防抖
                handler.postDelayed(verificationCodeValidationRunnable, 500);
            }
        });

        // 验证码输入框失焦监听器
        etVerificationCode.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                // 取消前一个未执行的验证
                handler.removeCallbacks(verificationCodeValidationRunnable);
                // 获取当前输入值
                String code = etVerificationCode.getText().toString();
                // 立即执行验证
                if (!isValidVerificationCode(code) && !code.isEmpty()) {
                    // 取消之前的Toast任务
                    handler.removeCallbacks(verificationCodeToastRunnable);
                    // 创建新的Toast任务
                    verificationCodeToastRunnable = () -> {
                        Toast.makeText(ForgotActivity.this, "验证码格式不正确", Toast.LENGTH_SHORT).show();
                    };
                    // 延迟1秒执行
                    handler.postDelayed(verificationCodeToastRunnable, TOAST_DELAY_TIME);
                }
                updateButtonStatus();
            }
        });

        // 发送验证码按钮点击事件
        btnSendCode.setOnClickListener(v -> {
            // 取消前一个未执行的发送验证码任务
            handler.removeCallbacks(sendCodeRunnable);

            // 创建新的发送验证码任务
            sendCodeRunnable = () -> {
                String account = etAccount.getText().toString();
                if (!isValidAccount(account)) {
                    Toast.makeText(ForgotActivity.this, "请输入正确的账号", Toast.LENGTH_SHORT).show();
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
        // 清除所有未执行的任务，避免内存泄漏
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    /**
     * 验证账号格式
     */
    private boolean isValidAccount(String account) {
        // 简单示例：验证手机号格式
        return account.matches("1[3-9]\\d{9}");
    }

    /**
     * 验证验证码格式
     */
    private boolean isValidVerificationCode(String code) {
        // 简单示例：验证6位数字
        return code.matches("\\d{6}");
    }

    /**
     * 验证密码格式
     */
    private boolean isValidPassword(String password) {
        // 简单示例：验证密码长度至少6位
        return password.length() >= 6;
    }

    /**
     * 验证表单是否有效
     */
    private boolean isFormValid() {
        String account = etAccount.getText().toString();
        String code = etVerificationCode.getText().toString();
        String password = etNewPassword.getText().toString();
        String confirmPassword = etConfirmNewPassword.getText().toString();

        boolean isAccountValid = isValidAccount(account);
        boolean isCodeValid = isValidVerificationCode(code);
        boolean isPasswordValid = isValidPassword(password);
        boolean isConfirmPasswordValid = password.equals(confirmPassword) && !confirmPassword.isEmpty();

        return isAccountValid && isCodeValid && isPasswordValid && isConfirmPasswordValid;
    }

    /**
     * 更新按钮状态
     */
    private void updateButtonStatus() {
        String account = etAccount.getText().toString();
        String code = etVerificationCode.getText().toString();
        String password = etNewPassword.getText().toString();
        String confirmPassword = etConfirmNewPassword.getText().toString();

        boolean isAccountValid = isValidAccount(account);
        boolean isCodeValid = isValidVerificationCode(code);
        boolean isPasswordValid = isValidPassword(password);
        boolean isConfirmPasswordValid = password.equals(confirmPassword) && !confirmPassword.isEmpty();

        // 更新重置密码按钮状态
        btnResetPassword.setEnabled(isAccountValid && isCodeValid && isPasswordValid && isConfirmPasswordValid);
    }

    /**
     * 返回登录页面（带右进左出动画）
     */
    private void finishWithAnimation() {
        finish(); // 直接结束当前Activity，会触发下面的finish()方法中的动画
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void finish() {
        super.finish();
        // 确保在直接调用finish()时也有动画
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}