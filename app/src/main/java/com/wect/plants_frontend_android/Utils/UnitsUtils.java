package com.wect.plants_frontend_android.Utils;

import android.content.Context;
import android.util.TypedValue;

public class UnitsUtils {

    // dp -> px
    public static float dpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    // px -> dp
    public static float pxToDp(Context context, float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    // sp -> px
    public static float spToPx(Context context, float sp) {
        return sp * context.getResources().getDisplayMetrics().scaledDensity;
    }

    // px -> sp
    public static float pxToSp(Context context, float px) {
        return px / context.getResources().getDisplayMetrics().scaledDensity;
    }

    // 用 TypedValue 方式（更通用）
    public static float applyDimension(Context context, int unit, float value) {
        return TypedValue.applyDimension(unit, value, context.getResources().getDisplayMetrics());
    }
}