package com.march.common.exts;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import com.march.common.exts.barui.BottomBarHelper;
import com.march.common.exts.barui.QMUIStatusBarHelper;
import com.march.common.exts.barui.StatusBarColorHelper;

/**
 * CreateAt : 2018/8/5
 * Describe :
 *
 * @author chendong
 */
public class BarUI {

    // 透明状态栏
    public static boolean canTranslucent(Activity activity) {
        return QMUIStatusBarHelper.canTranslucent(activity);
    }

    // 透明状态栏
    public static void translucent(Activity activity) {
        if (canTranslucent(activity)) {
            QMUIStatusBarHelper.translucent(activity);
        }
    }

    // 设置状态栏文字为黑色
    public static void setStatusBarLightMode(Activity activity) {
        QMUIStatusBarHelper.setStatusBarLightMode(activity);
    }

    // 设置状态栏文字为白色
    public static void setStatusBarDarkMode(Activity activity) {
        QMUIStatusBarHelper.setStatusBarDarkMode(activity);
    }

    // 设置状态栏颜色
    public static void setStatusBarColor(Activity activity, int color) {
        StatusBarColorHelper.setStatusBarColor(activity, color);
    }

    // 获取状态栏高度
    public static int getStatusbarHeight(Context context) {
        return QMUIStatusBarHelper.getStatusbarHeight(context);
    }

    // 是否有底部虚拟按键
    public static boolean hasBottomBar(Context context) {
        return BottomBarHelper.hasNavBar(context);
    }

    // 隐藏底部虚拟按键
    public static void hideBottomBar(Activity activity) {
        BottomBarHelper.hideBottomUI(activity);
    }

    // 设置底部虚拟按键颜色
    public static void setBottomBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setNavigationBarColor(color);
        }
    }
}
