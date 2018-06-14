package com.march.common;

import android.app.Application;
import android.content.Context;

import com.march.common.model.BuildConfig;
import com.march.common.model.WeakContext;
import com.march.common.utils.DimensUtils;
import com.march.common.utils.PathUtils;
import com.march.common.utils.ToastUtils;

/**
 * CreateAt : 2017/12/6
 * Describe :
 *
 * @author chendong
 */
public class Common {

    private static WeakContext sWeakContext;

    public static BuildConfig    BuildConfig;
    public static CommonInjector Injector;

    public static void init(Application application, CommonInjector injector) {
        sWeakContext = new WeakContext(application);
        Injector = injector;
        BuildConfig = new BuildConfig(Injector.getConfigClass());
        DimensUtils.init();
        ToastUtils.init(new ToastUtils.Config());
        PathUtils.init(application, BuildConfig.APPLICATION_ID);
    }

    public static Context getContext() {
        return sWeakContext.get();
    }

}
