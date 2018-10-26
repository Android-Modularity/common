package com.march.common.exts;

import android.graphics.Bitmap;

import com.march.common.able.Destroyable;
import com.march.common.able.Recyclable;
import com.march.common.able.Releasable;

import java.io.Closeable;
import java.io.IOException;

/**
 * CreateAt : 2017/12/6
 * Describe : 回收资源的工具类
 *
 * @author chendong
 */
public class RecycleX {


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
     *
     * @param bitmaps bitmap list
     * @return 回收成功
     */
    public static boolean recycle(Bitmap... bitmaps) {
        for (Bitmap bitmap : bitmaps) {
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
        return true;
    }


    // 回收资源
    public static boolean recycle(Recyclable... recyclables) {
        for (Recyclable recyclable : recyclables) {
            if (recyclable != null && !recyclable.isRecycled()) {
                recyclable.recycle();
            }
        }
        return true;
    }

    // 回收资源
    public static boolean recycle(Releasable... recyclables) {
        for (Releasable recyclable : recyclables) {
            if (recyclable != null) {
                recyclable.release();
            }
        }
        return true;
    }

    // 回收资源
    public static boolean recycle(Destroyable... destroyables) {
        for (Destroyable destroyable : destroyables) {
            if (destroyable != null) {
                destroyable.onDestroy();
            }
        }
        return true;
    }
}
