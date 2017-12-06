package com.march.common.utils;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

/**
 * CreateAt : 2017/4/24
 * Describe : View 辅助
 *
 * @author chendong
 */
public class ViewUtils {

    public static void setVisibility(View view, int visible) {
        if (view.getVisibility() != visible) {
            view.setVisibility(visible);
        }
    }

    /**
     * 为 View 设置背景
     *
     * @param view     view
     * @param drawable 背景 drawable
     */
    public static void setBackground( View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            view.setBackground(drawable);
        else
            view.setBackgroundDrawable(drawable);
    }

    /**
     * 设置 margin
     * @param top top
     * @param right right
     * @param bottom bottom
     * @param left left
     * @param views 控件列表
     */
    public static void setMargin(int top, int right, int bottom, int left, View... views) {
        ViewGroup.MarginLayoutParams layoutParams;
        for (View view : views) {
            layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            layoutParams.topMargin = top;
            layoutParams.rightMargin = right;
            layoutParams.bottomMargin = bottom;
            layoutParams.leftMargin = left;
            view.setLayoutParams(layoutParams);
            view.postInvalidate();
        }
    }

    /**
     * 给一些控件设置统一的宽高
     *
     * @param width  宽度
     * @param height 高度
     * @param views  控件列表
     */
    public static void setLayoutParam(int width, int height, View... views) {
        ViewGroup.LayoutParams layoutParams;
        for (View view : views) {
            layoutParams = view.getLayoutParams();
            layoutParams.width = width;
            layoutParams.height = height;
            view.setLayoutParams(layoutParams);
            view.postInvalidate();
        }
    }
}
