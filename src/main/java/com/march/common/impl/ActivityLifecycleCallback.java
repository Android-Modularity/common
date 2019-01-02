package com.march.common.impl;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * CreateAt : 2018/6/15
 * Describe :
 *
 * @author chendong
 */
public class ActivityLifecycleCallback implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        //onActivityCreated
    }

    @Override
    public void onActivityStarted(Activity activity) {
        //onActivityStarted
    }

    @Override
    public void onActivityResumed(Activity activity) {
        //onActivityResumed
    }

    @Override
    public void onActivityPaused(Activity activity) {
        //onActivityPaused
    }

    @Override
    public void onActivityStopped(Activity activity) {
        //onActivityStopped
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        //onActivitySaveInstanceState
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        //onActivityDestroyed
    }
}
