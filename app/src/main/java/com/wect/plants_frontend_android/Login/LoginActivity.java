package com.wect.plants_frontend_android.Login;

import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.wect.plants_frontend_android.R;

public class LoginActivity extends AppCompatActivity {

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


    }
}