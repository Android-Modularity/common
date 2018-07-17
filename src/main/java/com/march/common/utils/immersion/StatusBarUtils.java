package com.march.common.utils.immersion;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;

import java.lang.reflect.Field;

/**
 * CreateAt : 2018/6/13
 * Describe :
 *
 * @author chendong
 */
public class StatusBarUtils {

    public static void translucent(Activity activity) {
        ImmersionStatusBarUtils.translucent(activity);
    }


    public static void setStatusBarLightMode(Activity activity) {
        ImmersionStatusBarUtils.setStatusBarLightMode(activity);
    }

    public static void setStatusBarDarkMode(Activity activity) {
        ImmersionStatusBarUtils.setStatusBarDarkMode(activity);
    }

    private static int STATUS_BAR_HEIGHT = 0;

    public static int getStatusBarHeight(Context context) {
        if(STATUS_BAR_HEIGHT > 0){
            return STATUS_BAR_HEIGHT;
        }
        int height = 0;

        // 第一种方案，通过资源ID
        final Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = resources.getDimensionPixelSize(resourceId);
        }

        // 第二种方案，反射
        if (height <= 0) {
            try {
                @SuppressLint("PrivateApi")
                Class<?> cls = Class.forName("com.android.internal.R$dimen");
                Object obj = cls.newInstance();
                Field field = cls.getField("status_bar_height");
                int x = Integer.parseInt(field.get(obj).toString());
                return context.getResources().getDimensionPixelSize(x);
            } catch (Exception ignore) {

            }
        }

        // 通用默认值
        if (height <= 0) {
            height = (int) Math.ceil((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? 24 : 25) * resources.getDisplayMetrics().density);
        }
        if(height>0){
            STATUS_BAR_HEIGHT = height;
        }
        return height;
    }
}
