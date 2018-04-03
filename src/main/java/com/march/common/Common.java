package com.march.common;

import android.app.Application;
import android.content.Context;

import com.march.common.adapter.JsonParseAdapter;
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
    private static JsonParseAdapter sJsonParseAdapter;

    public static void init(Application application, JsonParseAdapter sJsonParseAdapter) {
        sWeakContext = new WeakContext(application);
        sJsonParseAdapter = sJsonParseAdapter;
        DimensUtils.init();
        ToastUtils.init(new ToastUtils.Config());
    }

    public static Context getContext() {
        return sWeakContext.get();
    }

    public static JsonParseAdapter getJsonParseAdapter() {
        return sJsonParseAdapter;
    }
}
