package com.march.common.pool;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CreateAt : 2018/6/17
 * Describe :
 *
 * @author chendong
 */
public class ExecutorsPool {

    private static volatile ExecutorsPool sInst;

    private ExecutorService mCacheExecutor;
    private ExecutorService mSingleExecutor;
    private Handler         mHandler;

    private ExecutorsPool() {

    }

    public static ExecutorsPool getInst() {
        if (sInst == null) {
            synchronized (ExecutorsPool.class) {
                if (sInst == null) {
                    sInst = new ExecutorsPool();
                }
            }
        }
        return sInst;
    }

    public static void bg(Runnable runnable) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            runnable.run();
        } else {
            getInst().cache().execute(runnable);
        }
    }

    public static void ui(Runnable runnable) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            getInst().ui().post(runnable);
        } else {
            runnable.run();
        }
    }

    public static void ui(Runnable runnable, long delay) {
        getInst().ui().postDelayed(runnable, delay);
    }

    public ExecutorService cache() {
        if (mCacheExecutor == null) {
            mCacheExecutor = Executors.newCachedThreadPool();
        }
        return mCacheExecutor;
    }

    public ExecutorService single() {
        if (mSingleExecutor == null) {
            mSingleExecutor = Executors.newSingleThreadExecutor();
        }
        return mSingleExecutor;
    }

    public Handler ui() {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        return mHandler;
    }

}
