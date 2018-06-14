package com.march.common.utils.immersion;

import android.app.Activity;

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
}
