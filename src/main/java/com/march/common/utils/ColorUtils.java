package com.march.common.utils;

import android.graphics.Color;

/**
 * CreateAt : 2018/6/18
 * Describe :
 *
 * @author chendong
 */
public class ColorUtils {

    public static int parseColor(String color, int def) {
        try {
            return Color.parseColor(color);
        } catch (Exception e) {
            return def;
        }
    }
}
