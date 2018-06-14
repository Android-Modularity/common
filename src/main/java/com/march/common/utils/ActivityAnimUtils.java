package com.march.common.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;

import com.march.common.R;

/**
 * CreateAt : 2016/11/30
 * Describe : Activity 动画工具类
 *
 * @author luyuan
 */
@TargetApi(Build.VERSION_CODES.ECLAIR)
public class ActivityAnimUtils {

    public static void translateStart(Activity activity) {
        activity.overridePendingTransition(R.anim.translate_in, R.anim.no_anim);
    }

    public static void translateFinish(Activity activity) {
        activity.overridePendingTransition(R.anim.no_anim, R.anim.translate_out);
    }

    public static void fadeStart(Activity activity) {
//        activity.overridePendingTransition(R.anim.fade_in, R.anim.no_anim);
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public static void fadeFinish(Activity activity) {
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
