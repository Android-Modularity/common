package com.march.common.x;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.march.common.Common;

/**
 * CreateAt : 2018/8/3
 * Describe :
 *
 * @author chendong
 */
public class SizeX {

    public static int   WIDTH          = 0;
    public static int   HEIGHT         = 0;
    public static float DENSITY        = 0;
    public static float SCALED_DENSITY = 0;

    public static void init() {
        DisplayMetrics metrics = getDisplayMetrics();
        WIDTH = metrics.widthPixels;
        HEIGHT = metrics.heightPixels;
        DENSITY = metrics.density;
        SCALED_DENSITY = metrics.scaledDensity;
    }

    public static DisplayMetrics getDisplayMetrics() {
        return Common.app().getResources().getDisplayMetrics();
    }

    /**
     * dp转px
     *
     * @param dpVal dp值
     * @return px值
     */
    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getDisplayMetrics());
    }

    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param spVal sp 值
     * @return px 值
     */
    public static int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param pxVal px 值
     * @return dp值
     */
    public static float px2dp(float pxVal) {
        return pxVal / DENSITY;
    }

    /**
     * px转sp
     *
     * @param pxVal px值
     * @return sp值
     */
    public static float px2sp(float pxVal) {
        return pxVal / SCALED_DENSITY;
    }

}
