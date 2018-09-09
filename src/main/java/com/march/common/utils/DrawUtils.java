package com.march.common.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.view.View;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * CreateAt : 7/13/17
 * Describe :
 *
 * @author chendong
 */
public class DrawUtils {

    /**
     * 创建一个画板
     *
     * @param bitmap 图像
     * @return canvas
     */
    public static Canvas newCanvas(Bitmap bitmap) {
        Canvas canvas;
        if (bitmap != null)
            canvas = new Canvas(bitmap);
        else
            canvas = new Canvas();
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        return canvas;
    }


    public static Paint newPaint(int color, int width, Paint.Style style) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(style);
        if (width > 0)
            paint.setStrokeWidth(width);
        initAntiAliasPaint(paint);
        return paint;
    }


    public static void initRoundPaint(Paint paint) {
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    public static void initAntiAliasPaint(Paint paint) {
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
    }


    /**
     * 在canvas上面绘制view的图层，根据宽高自动转换，并进行资源回收
     *
     * @param canvas 画板
     * @param paint  笔
     * @param view   控件
     * @param width  宽度
     * @param height 高度
     */
    public static void drawViewOnCanvas(Canvas canvas, Paint paint, View view, int width, int height) {
        view.setDrawingCacheEnabled(true);
        Bitmap drawingCache = view.getDrawingCache();
        Bitmap scaledBitmap;
        if (drawingCache.getWidth() != width || drawingCache.getHeight() != height)
            scaledBitmap = Bitmap.createScaledBitmap(drawingCache, width, height, true);
        else
            scaledBitmap = drawingCache;
        canvas.drawBitmap(scaledBitmap, 0, 0, paint);
        view.setDrawingCacheEnabled(false);
        BitmapUtils.recycleBitmaps(drawingCache, scaledBitmap);
    }


    public static void drawViewOnCanvasNoScale(Canvas canvas, Paint paint, View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap drawingCache = view.getDrawingCache();
        canvas.drawBitmap(drawingCache, 0, 0, paint);
        view.setDrawingCacheEnabled(false);
        BitmapUtils.recycleBitmaps(drawingCache);
    }


    public static void drawHLine(Canvas canvas, Paint paint, float y, float fromX, float width) {
        canvas.drawLine(fromX, y, fromX + width, y, paint);
    }

    public static void drawVLine(Canvas canvas, Paint paint, float x, float fromY, float height) {
        canvas.drawLine(x, fromY, x, fromY + height, paint);
    }


    //获取字体高度
    public static float measureTextHeight(Paint p) {
        return Math.abs(p.ascent() + p.descent());
    }

}
