package com.march.common.extensions;

import android.app.Activity;
import android.content.Context;

import com.march.common.extensions.barui.BottomBarHelper;
import com.march.common.extensions.barui.QMUIStatusBarHelper;
import com.march.common.extensions.barui.StatusBarColorHelper;

/**
 * CreateAt : 2018/8/5
 * Describe :
 *
 * @author chendong
 */
public class BarUI {

    // 透明状态栏
    public static void translucent(Activity activity) {
        QMUIStatusBarHelper.translucent(activity);
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


}
