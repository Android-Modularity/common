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
    private static ExecutorsPool sInst;


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


    private ExecutorsPool() {

    }

    ExecutorService mCacheExecutor;
    ExecutorService mSingleExecutor;
    Handler         mHandler;

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

    public void execute(Runnable runnable) {
        cache().execute(runnable);
    }

    public void executeOnBg(Runnable runnable) {
        cache().execute(runnable);
    }

    public void executeOnUI(Runnable runnable) {
        ui().post(runnable);
    }

    public void executeOnUI(Runnable runnable, long delay) {
        ui().postDelayed(runnable, delay);
    }
}
