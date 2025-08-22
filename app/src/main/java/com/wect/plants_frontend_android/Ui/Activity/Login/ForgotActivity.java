package com.wect.plants_frontend_android.Ui.Activity.Login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.OnBackPressedCallback;

import com.wect.plants_frontend_android.R;
import com.wect.plants_frontend_android.Utils.LoginUiUtils;
import com.wect.plants_frontend_android.Utils.ToastUtil;
import com.wect.plants_frontend_android.Ui.Activity.Based.BaseActivity;

public class ForgotActivity extends BaseActivity {

    private Button btnSendCode;
    private Button btnResetPassword;
    private Button registerToLogin;
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
        setContentView(R.layout.activity_forgot_password);

        // 初始化控件
        initViews();

        // 设置监听器
        setListeners();

        //实现模糊效果
        View leftBlurView = findViewById(R.id.login_bg_left);
        View rightBlurView = findViewById(R.id.login_bg_right);
        LoginUiUtils.LoginBackgroundBlur(leftBlurView, rightBlurView);


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
        // 账号输入框
        etAccount = findViewById(R.id.forgot_account);

        // 验证码
        btnSendCode = findViewById(R.id.forogt_btn_send_code);
        etVerificationCode = findViewById(R.id.et_verification_code);

        // 新密码和确认密码
        etNewPassword = findViewById(R.id.forogt_password);
        etConfirmNewPassword = findViewById(R.id.forgot_confirm_password);
        btnResetPassword = findViewById(R.id.forgot_btn_forgot);

        // 登录按钮
        registerToLogin = findViewById(R.id.register_to_login);

        // 初始化Handler（使用主线程Looper）
        handler = new Handler(Looper.getMainLooper());
    }

    /**
     * 设置监听器
     */
    private void setListeners() {
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
                if (!LoginUiUtils.isValidPhoneNumber(password) && !password.isEmpty()) {
                    // 取消之前的Toast任务
                    handler.removeCallbacks(newPasswordToastRunnable);
                    // 创建新的Toast任务
                    newPasswordToastRunnable = () -> {
                        ToastUtil.show(this, "密码长度不能少于6位");
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
                        ToastUtil.show(this, "两次输入的密码不一致");
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
                ToastUtil.show(this, "密码重置成功");
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
                if (!LoginUiUtils.isValidPhoneNumber(account) && !account.isEmpty()) {
                    // 取消之前的Toast任务
                    handler.removeCallbacks(accountToastRunnable);
                    // 创建新的Toast任务
                    accountToastRunnable = () -> {
                        ToastUtil.show(this, "请输入正确的账号");
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
                if (!LoginUiUtils.isValidVerificationCode(code) && !code.isEmpty()) {
                    // 取消之前的Toast任务
                    handler.removeCallbacks(verificationCodeToastRunnable);
                    // 创建新的Toast任务
                    verificationCodeToastRunnable = () -> {
                        ToastUtil.show(this, "验证码格式不正确");
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
                if (!LoginUiUtils.isValidPhoneNumber(account)) {
                    ToastUtil.show(this, "请输入正确的账号");
                    return;
                }

                if (!isCounting) {
                    LoginUiUtils.startCountDown(btnSendCode,60000,1000);
                    // 模拟发送验证码，实际项目中这里应该是请求服务器的代码
                    simulateSendVerificationCode();
                }
            };

            // 延迟300ms执行，实现防抖
            handler.postDelayed(sendCodeRunnable, 300);
        });

        // 登录按钮跳转
        registerToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册用户界面
                finishWithAnimation();
            }
        });
    }

    /**
     * 模拟发送验证码（实际项目中替换为网络请求）
     */
    private void simulateSendVerificationCode() {
        btnSendCode.postDelayed(() -> {
            etVerificationCode.setHint("123456");
            ToastUtil.show(this, "验证码已发送");
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
     * 验证表单是否有效
     */
    private boolean isFormValid() {
        String account = etAccount.getText().toString();
        String code = etVerificationCode.getText().toString();
        String password = etNewPassword.getText().toString();
        String confirmPassword = etConfirmNewPassword.getText().toString();

        boolean isAccountValid = LoginUiUtils.isValidPhoneNumber(account);
        boolean isCodeValid = LoginUiUtils.isValidVerificationCode(code);
        boolean isPasswordValid = LoginUiUtils.isValidPassword(password);
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

        boolean isAccountValid = LoginUiUtils.isValidPhoneNumber(account);
        boolean isCodeValid = LoginUiUtils.isValidVerificationCode(code);
        boolean isPasswordValid = LoginUiUtils.isValidPassword(password);
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