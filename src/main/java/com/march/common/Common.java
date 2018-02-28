package com.march.common;

import android.app.Application;
import android.content.Context;

import com.march.common.model.WeakContext;
import com.march.common.utils.DimensUtils;
import com.march.common.utils.ToastUtils;

import java.lang.ref.WeakReference;

/**
 * CreateAt : 2017/12/6
 * Describe :
 *
 * @author chendong
 */
public class Common {

    private static WeakContext sWeakContext;

    public static void init(Application application ) {
        sWeakContext = new WeakContext(application);
        DimensUtils.init();
        ToastUtils.init(new ToastUtils.Config());
    }

    public static Context getContext() {
        return sWeakContext.get();
    }

}
