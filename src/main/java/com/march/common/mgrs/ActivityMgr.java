package com.march.common.mgrs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.march.common.x.EmptyX;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * CreateAt : 2018/11/19
 * Describe :
 *
 * @author chendong
 */
public class ActivityMgr implements IMgr {

    private static volatile ActivityMgr sInst;

    public  static ActivityMgr getInst() {
        if (sInst == null) {
            synchronized (ActivityMgr.class) {
                if (sInst == null) {
                    sInst = new ActivityMgr();
                }
            }

        }
        return sInst;
    }


    private ActivityMgr() {

    }

    private ActivityLifecycleImpl mActivityLifecycle = new ActivityLifecycleImpl();

    public void init(Application application) {
        application.registerActivityLifecycleCallbacks(mActivityLifecycle);
    }

    public @Nullable
    Activity getTopActivity() {
        return mActivityLifecycle.getTopActivity();
    }

    public void removeAll() {
        List<WeakReference<Activity>> activityList = getActivityList();
        if (!EmptyX.isEmpty(activityList)) {
            for (WeakReference<Activity> activityWeakReference : activityList) {
                if (activityWeakReference != null && activityWeakReference.get() != null) {
                    Activity activity = activityWeakReference.get();
                    activity.finish();
                }
            }
        }
        activityList.clear();
    }

    public List<WeakReference<Activity>> getActivityList() {
        return mActivityLifecycle.mActivityList;
    }


    static class ActivityLifecycleImpl implements Application.ActivityLifecycleCallbacks {

        final LinkedList<WeakReference<Activity>> mActivityList = new LinkedList<>();

        private int mForegroundCount = 0;
        private int mConfigCount = 0;

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            setTopActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            setTopActivity(activity);
            if (mForegroundCount <= 0) {
                // postStatus(true);
            }
            if (mConfigCount < 0) {
                ++mConfigCount;
            } else {
                ++mForegroundCount;
            }
        }

        @Override
        public void onActivityResumed(Activity activity) {
            setTopActivity(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            if (activity.isChangingConfigurations()) {
                --mConfigCount;
            } else {
                --mForegroundCount;
                if (mForegroundCount <= 0) {
                    // postStatus(false);
                }
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            WeakReference<Activity> activityWeakReference = find(activity);
            if (activityWeakReference != null) {
                mActivityList.remove(activityWeakReference);
            }
        }

        WeakReference<Activity> find(Activity activity) {
            Activity weakAct;
            for (WeakReference<Activity> activityWeakReference : mActivityList) {
                weakAct = activityWeakReference.get();
                if (weakAct != null && weakAct.equals(activity)) {
                    return activityWeakReference;
                }
            }
            return null;
        }

        private void setTopActivity(final Activity activity) {
            WeakReference<Activity> activityWeakReference = find(activity);
            if (activityWeakReference == null) {
                mActivityList.addLast(new WeakReference<>(activity));
            } else {
                mActivityList.remove(activityWeakReference);
                mActivityList.addLast(activityWeakReference);
            }
        }

        Activity getTopActivity() {
            if (!mActivityList.isEmpty()) {
                WeakReference<Activity> last = mActivityList.getLast();
                if (last != null && last.get() != null) {
                    return last.get();
                }
            }
            // using reflect to get top activity
            try {
                @SuppressLint("PrivateApi")
                Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
                Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
                Field activitiesField = activityThreadClass.getDeclaredField("mActivityList");
                activitiesField.setAccessible(true);
                Map activities = (Map) activitiesField.get(activityThread);
                if (activities == null) return null;
                for (Object activityRecord : activities.values()) {
                    Class activityRecordClass = activityRecord.getClass();
                    Field pausedField = activityRecordClass.getDeclaredField("paused");
                    pausedField.setAccessible(true);
                    if (!pausedField.getBoolean(activityRecord)) {
                        Field activityField = activityRecordClass.getDeclaredField("activity");
                        activityField.setAccessible(true);
                        Activity activity = (Activity) activityField.get(activityRecord);
                        setTopActivity(activity);
                        return activity;
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    @Override
    public void recycle() {

    }

    @Override
    public boolean isRecycled() {
        return false;
    }
}
