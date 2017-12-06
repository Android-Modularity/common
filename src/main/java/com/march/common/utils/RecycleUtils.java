package com.march.common.utils;

import android.graphics.Bitmap;

import java.io.Closeable;
import java.io.IOException;

/**
 * CreateAt : 2017/12/6
 * Describe :
 *
 * @author chendong
 */
public class RecycleUtils {
    /**
     * 关闭读写流
     *
     * @param closeable 读写流
     * @return 关闭成功
     */
    public static boolean recycle(Closeable... closeable) {
        for (Closeable close : closeable) {
            if (close != null)
                try {
                    close.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return true;
    }

    /**
     * 回收bitmap
     * @param bitmaps bitmap list
     * @return 回收成功
     */
    public static boolean recycle(Bitmap... bitmaps) {
        for (Bitmap bitmap : bitmaps) {
            if (bitmap != null && !bitmap.isRecycled())
                bitmap.recycle();
        }
        return true;
    }
}
