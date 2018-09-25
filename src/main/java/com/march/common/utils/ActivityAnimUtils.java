package com.march.common.utils;

import android.app.Activity;

import com.march.common.R;

/**
 * CreateAt : 2016/11/30
 * Describe : Activity 动画工具类
 *
 * @author luyuan
 */
public class ActivityAnimUtils {

    public static void translateStart(Activity activity) {
        activity.overridePendingTransition(R.anim.act_translate_in, R.anim.act_no_anim);
    }

    public static void translateFinish(Activity activity) {
        activity.overridePendingTransition(R.anim.act_no_anim, R.anim.act_translate_out);
    }

    public static void fadeStart(Activity activity) {
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public static void fadeFinish(Activity activity) {
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
