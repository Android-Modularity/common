package com.march.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;


/**
 * CreateAt : 7/11/17
 * Describe : 使用代码创建shape
 *
 * @author chendong
 */
public class DrawableUtils {

    public static Drawable getDrawable(Context context, int drawableRes) {
        return context.getResources().getDrawable(drawableRes);
    }

    /**
     * 生成横纵平铺的图片
     * @param context ctx
     * @param bitmap 位图
     * @param width 容器宽度
     * @param widthScale 平铺图片占据容器的宽度
     * @param aspectRatio 平铺图片的宽高比
     * @return drawable
     */
    public static Drawable newRepeatXYDrawable(Context context, Bitmap bitmap, int width, float widthScale, float aspectRatio) {
        int realWidth = (int) (width * widthScale);
        int realHeight = (int) (realWidth * aspectRatio);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, realWidth, realHeight, true);
        // 设置内容区域平铺的小圆角背景
        BitmapDrawable drawable = new BitmapDrawable(context.getResources(), scaledBitmap);
        drawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        return drawable;
    }

    /**
     * 创建圆角矩形 drawable
     *
     * @param color      颜色
     * @param radiusInDp 半径
     * @return drawable
     */
    public static Drawable newRoundRectDrawable(int color, int radiusInDp) {
        GradientDrawable gd = new GradientDrawable();// 创建drawable
        gd.setColor(color);
        gd.setCornerRadius(DimensUtils.dp2px(radiusInDp));
        return gd;
    }

    /**
     * 创建状态 selected drawable
     *
     * @param context     context
     * @param selectRes   selected = true  res
     * @param unSelectRes selected = false res
     * @return drawable
     */
    public static StateListDrawable newSelectStateDrawable(Context context, int selectRes, int unSelectRes) {
        Drawable selectDrawable = context.getResources().getDrawable(selectRes);
        Drawable unSelectDrawable = getDrawable(context, unSelectRes);
        StateListDrawable stateList = new StateListDrawable();
        int stateSelected = android.R.attr.state_selected;
        stateList.addState(new int[]{stateSelected}, selectDrawable);
        stateList.addState(new int[]{stateSelected}, unSelectDrawable);
        stateList.addState(new int[]{}, unSelectDrawable);
        return stateList;
    }

    /**
     * 创建状态 pressed
     *
     * @param context    context
     * @param pressRes   pressed = true res
     * @param releaseRes pressed = false res
     * @return drawable
     */
    public static StateListDrawable newPressedDrawable(Context context, int pressRes, int releaseRes) {
        Drawable pressDrawable = getDrawable(context, pressRes);
        Drawable releaseDrawable = getDrawable(context, releaseRes);
        StateListDrawable stateList = new StateListDrawable();
        int statePressed = android.R.attr.state_pressed;
        stateList.addState(new int[]{statePressed}, pressDrawable);
        stateList.addState(new int[]{statePressed}, releaseDrawable);
        stateList.addState(new int[]{}, releaseDrawable);
        return stateList;
    }
}
