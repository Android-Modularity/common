package com.march.common.x;

import android.animation.Animator;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.animation.Animation;

import com.march.common.able.Destroyable;
import com.march.common.able.Recyclable;
import com.march.common.able.Releasable;

import java.io.Closeable;
import java.io.IOException;
import java.lang.ref.Reference;
import java.util.Collection;
import java.util.Timer;

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


    /**
     * 回收资源
     *
     * @param recyclables recyclable list
     * @return 回收结果
     */
    public static boolean recycle(Recyclable... recyclables) {
        for (Recyclable recyclable : recyclables) {
            if (recyclable != null && !recyclable.isRecycled()) {
                recyclable.recycle();
            }
        }
        return true;
    }

    /**
     * 回收资源
     *
     * @param recyclables releasable
     * @return 是否回收成功
     */
    public static boolean recycle(Releasable... recyclables) {
        for (Releasable recyclable : recyclables) {
            if (recyclable != null) {
                recyclable.release();
            }
        }
        return true;
    }

    /**
     * 回收资源
     *
     * @param destroyables Destroyable
     * @return 是否回收成功
     */
    public static boolean recycle(Destroyable... destroyables) {
        for (Destroyable destroyable : destroyables) {
            if (destroyable != null) {
                destroyable.onDestroy();
            }
        }
        return true;
    }


    /**
     * 取消 Handler 事件
     * @param handlers handler
     */
    public static void recycle(Handler... handlers) {
        for (Handler handler : handlers) {
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
            }
        }
    }


    /**
     * 取消动画播放
     *
     * @param animators 动画列表
     */
    public static void recycle(Animator... animators) {
        for (Animator animator : animators) {
            if (animator != null && animator.isRunning()) {
                animator.cancel();
                animator.end();
                animator.removeAllListeners();
            }
        }
    }

    /**
     * 回收动画对象
     *
     * @param animations animation
     */
    public static void recycle(Animation... animations) {
        for (Animation animation : animations) {
            if (animation != null) {
                animation.reset();
                animation.cancel();
                animation.setAnimationListener(null);
            }
        }
    }

    /**
     * 回收 Timer 对象
     *
     * @param timers timers
     */
    public static void recycle(Timer... timers) {
        for (Timer timer : timers) {
            if (timer != null) {
                timer.cancel();
            }
        }
    }

    /**
     * 回收 Task 对象
     *
     * @param asyncTasks asyncTasks
     */
    public static void recycle(AsyncTask... asyncTasks) {
        for (AsyncTask task : asyncTasks) {
            if (task != null) {
                task.cancel(true);
            }
        }
    }

    /**
     * 回收引用对象
     *
     * @param references references
     */
    public static void recycle(Reference... references) {
        for (Reference reference : references) {
            if (reference != null) {
                reference.clear();
            }
        }
    }


    /**
     * 回收列表对象
     *
     * @param collections collections
     */
    public static void recycle(Collection... collections) {
        for (Collection collection : collections) {
            if (collection != null && !collection.isEmpty()) {
                collection.clear();
            }
        }
    }

}
