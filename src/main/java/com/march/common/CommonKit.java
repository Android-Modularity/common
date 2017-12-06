package com.march.common;

import android.app.Application;
import android.content.Context;

import com.march.common.utils.DimensUtils;
import com.march.common.utils.ToastUtils;

/**
 * CreateAt : 2017/12/6
 * Describe :
 *
 * @author chendong
 */
public class CommonKit {

    private static Context sContext;

    public static void init(Application application) {
        sContext = application;
        DimensUtils.init();
        ToastUtils.init(new ToastUtils.Config());
    }

    public static Context getContext() {
        return sContext;
    }
}
