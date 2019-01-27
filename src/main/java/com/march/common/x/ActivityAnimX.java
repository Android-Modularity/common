package com.march.common.x;

import android.app.Activity;

import com.march.common.R;

/**
 * CreateAt : 2016/11/30
 * Describe : Activity 动画工具类
 *
 * @author luyuan
 */
public class ActivityAnimX {

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

    public static void bottomTranslateStart(Activity activity) {
        activity.overridePendingTransition(R.anim.act_bottom_in, R.anim.act_no_anim);
    }

    public static void bottomTranslateFinish(Activity activity) {
        activity.overridePendingTransition(R.anim.act_no_anim, R.anim.act_bottom_out);
    }
}
