package com.wect.plants_frontend_android.Utils;

import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

public class LoginUiUtils {

    /**
     * 验证手机号格式
     * @param phone
     * @return
     */
    public static boolean isValidPhoneNumber(String phone) {
        return phone != null && phone.matches("^1[3-9]\\d{9}$");
    }

    /**
     * 验证验证码格式
     * @param code
     * @return
     */
    public static boolean isValidVerificationCode(String code) {
        return code != null && code.matches("^\\d{6}$");
    }

    /**
     * 验证密码格式
     * @param password
     * @return
     */
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

    /**
     * 开始60秒倒计时
     * @param button
     * @param totalMillis
     * @param intervalMillis
     * @return
     */
    public static CountDownTimer startCountDown(Button button, long totalMillis, long intervalMillis) {
        button.setEnabled(false);
        return new CountDownTimer(totalMillis, intervalMillis) {
            @Override
            public void onTick(long millisUntilFinished) {
                button.setText(millisUntilFinished / 1000 + "s后重发");
            }

            @Override
            public void onFinish() {
                button.setEnabled(true);
                button.setText("发送验证码");
            }
        }.start();
    }

    /**
     * 登录 注册 忘记密码 界面的背景模糊
     * @param leftBlurView
     * @param rightBlurView
     */
    public static void LoginBackgroundBlur (View leftBlurView, View rightBlurView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // 对左层应用模糊
            leftBlurView.setRenderEffect(
                    RenderEffect.createBlurEffect(300f, 300f, Shader.TileMode.CLAMP)
            );

            // 对右层应用模糊
            rightBlurView.setRenderEffect(
                    RenderEffect.createBlurEffect(300f, 300f, Shader.TileMode.CLAMP)
            );
        }
    }
}

