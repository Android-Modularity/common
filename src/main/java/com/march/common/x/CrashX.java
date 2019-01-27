package com.march.common.x;

/**
 * CreateAt : 7/13/17
 * Describe : 异常捕捉
 *
 * @author chendong
 */
public class CrashX {

    public static void init(final OnCrashListener listener) {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                LogX.e(e);
                if (listener != null) {
                    listener.onCrash(t, e);
                }
            }
        });
    }

    public interface OnCrashListener {
        void onCrash(Thread t, Throwable e);
    }
}
