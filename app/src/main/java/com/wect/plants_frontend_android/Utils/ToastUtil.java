package com.wect.plants_frontend_android.Utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wect.plants_frontend_android.R;

public class ToastUtil {


    /**
     * 带图标的消息
     * @param context
     * @param message
     * @param iconResId
     */
    public static void show(Context context, String message, int iconResId) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.custom_toast, null);

        // 设置文字
        TextView text = layout.findViewById(R.id.toast_text);
        text.setText(message);

        // 设置图标
        ImageView icon = layout.findViewById(R.id.toast_icon);
        if (iconResId != 0) {
            icon.setImageResource(iconResId);
        } else {
            icon.setVisibility(View.GONE);
        }

        // 创建 Toast（新 API 推荐用这种方式创建实例）
        Toast toast = new Toast(context.getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 200);

        // setView 依旧可用，但我们屏蔽警告（未来如果被移除可以换成 Dialog 或 Snackbar）
        @SuppressWarnings("deprecation")
        View deprecatedView = layout;
        toast.setView(deprecatedView);

        toast.show();
    }


    /**
     * 无图标的消息
     * @param context
     * @param message
     */
    public static void show(Context context, String message) {
        show(context, message, 0);
    }
}
