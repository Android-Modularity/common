package com.march.common.x;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresPermission;

import com.march.common.Common;
import com.march.common.x.barui.BottomBarHelper;
import com.march.common.x.barui.NotificationBarHelper;
import com.march.common.x.barui.QMUIStatusBarHelper;
import com.march.common.x.barui.StatusBarColorHelper;

import static android.Manifest.permission.EXPAND_STATUS_BAR;

/**
 * CreateAt : 2018/8/5
 * Describe :
 *
 * @author chendong
 */
public class BarUI {

    /**
     * 透明状态栏
     *
     * @param activity act
     * @return 是否可以透明状态栏
     */
    public static boolean canTranslucent(Activity activity) {
        return QMUIStatusBarHelper.canTranslucent(activity);
    }

    /**
     * 透明状态栏
     *
     * @param activity act
     */
    public static void translucent(Activity activity) {
        if (canTranslucent(activity)) {
            QMUIStatusBarHelper.translucent(activity);
        }
    }

    /**
     * 设置状态栏文字为黑色
     * @param activity act
     */
    public static boolean setStatusBarLightMode(Activity activity) {
        return QMUIStatusBarHelper.setStatusBarLightMode(activity);
    }

    /**
     * 设置状态栏文字为白色
     * @param activity act
     */
    public static void setStatusBarDarkMode(Activity activity) {
        QMUIStatusBarHelper.setStatusBarDarkMode(activity);
    }

    /**
     * 设置状态栏颜色
     * @param activity act
     * @param color 颜色
     */
    public static boolean setStatusBarColor(Activity activity, int color) {
        return StatusBarColorHelper.setStatusBarColor(activity, color);
    }

    /**
     * 获取状态栏高度
     * @param context ctx
     * @return 状态栏高度
     */
    public static int getStatusbarHeight(Context context) {
        return QMUIStatusBarHelper.getStatusbarHeight(context);
    }

    /**
     * 是否有底部虚拟按键
     * @param context ctx
     * @return 是否有 bottom bar
     */
    public static boolean hasBottomBar(Context context) {
        return BottomBarHelper.hasNavBar(context);
    }

    /**
     * 获取底部虚拟按键高度
     *
     * @return 虚拟按键高度
     */
    public static int getBottomBarHeight() {
        if (hasBottomBar(Common.app())) {
            return BottomBarHelper.getBottomBarHeight(Common.app());
        }
        return 0;
    }

    /**
     * 隐藏底部虚拟按键
     * @param activity act
     */
    public static void hideBottomBar(Activity activity) {
        BottomBarHelper.hideBottomUI(activity);
    }

    /**
     * 设置底部虚拟按键颜色
     * @param activity act
     * @param color 颜色
     */
    public static void setBottomBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setNavigationBarColor(color);
        }
    }


    @RequiresPermission(EXPAND_STATUS_BAR)
    public static void toggleNotificationBar(final boolean isVisible) {
        NotificationBarHelper.toggleNotificationBar(isVisible);
    }

}
