package com.march.common.x;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

/**
 * CreateAt : 2018/8/2
 * Describe : Activity, AppCompatActivity, Fragment, v4.Fragment 混合
 *
 * @author chendong
 */
public class AppUIMixin {

    private Activity                        appActivity;
    private AppCompatActivity               supportActivity;
    private Fragment                        appFragment;
    private android.support.v4.app.Fragment supportFragment;

    public static AppUIMixin from(Object object) {
        if (object instanceof AppCompatActivity) {
            return from(((AppCompatActivity) object));
        } else if (object instanceof Activity) {
            return from(((Activity) object));
        } else if (object instanceof Fragment) {
            return from(((Fragment) object));
        } else if (object instanceof android.support.v4.app.Fragment) {
            return from(((android.support.v4.app.Fragment) object));
        }
        return new AppUIMixin();
    }


    public static AppUIMixin from(AppCompatActivity supportActivity) {
        AppUIMixin appUIMixin = new AppUIMixin();
        appUIMixin.supportActivity = supportActivity;
        return appUIMixin;
    }

    public static AppUIMixin from(Activity appActivity) {
        AppUIMixin appUIMixin = new AppUIMixin();
        appUIMixin.appActivity = appActivity;
        return appUIMixin;
    }

    public static AppUIMixin from(Fragment appFragment) {
        AppUIMixin appUIMixin = new AppUIMixin();
        appUIMixin.appFragment = appFragment;
        return appUIMixin;
    }

    public static AppUIMixin from(android.support.v4.app.Fragment supportFragment) {
        AppUIMixin appUIMixin = new AppUIMixin();
        appUIMixin.supportFragment = supportFragment;
        return appUIMixin;
    }

    public void startActivity(Intent intent) {
        if (appActivity != null) {
            appActivity.startActivity(intent);
        }
        if (supportActivity != null) {
            supportActivity.startActivity(intent);
        }
        if (appFragment != null) {
            appFragment.startActivity(intent);
        }
        if (supportFragment != null) {
            supportFragment.startActivity(intent);
        }
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        try {
            if (appActivity != null) {
                appActivity.startActivityForResult(intent, requestCode);
            }
            if (supportActivity != null) {
                supportActivity.startActivityForResult(intent, requestCode);
            }
            if (appFragment != null) {
                appFragment.startActivityForResult(intent, requestCode);
            }
            if (supportFragment != null) {
                supportFragment.startActivityForResult(intent, requestCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Activity getActivity() {
        if (appActivity != null) {
            return appActivity;
        }
        if (supportActivity != null) {
            return supportActivity;
        }
        if (appFragment != null) {
            return appFragment.getActivity();
        }
        if (supportFragment != null) {
            return supportFragment.getActivity();
        }
        return null;
    }

    public Context getContext() {
        if (appActivity != null) {
            return appActivity.getApplicationContext();
        }
        if (supportActivity != null) {
            return supportActivity.getApplicationContext();
        }
        if (appFragment != null && appFragment.getActivity() != null) {
            return appFragment.getActivity().getApplicationContext();
        }
        if (supportFragment != null && supportFragment.getActivity() != null) {
            return supportFragment.getActivity().getApplicationContext();
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermissions(int requestCode, String... permissions) {
        if (appActivity != null) {
            appActivity.requestPermissions(permissions, requestCode);
        }
        if (supportActivity != null) {
            supportActivity.requestPermissions(permissions, requestCode);
        }
        if (appFragment != null && appFragment.getActivity() != null) {
            appFragment.requestPermissions(permissions, requestCode);
        }
        if (supportFragment != null && supportFragment.getActivity() != null) {
            supportFragment.requestPermissions(permissions, requestCode);
        }
    }
}
