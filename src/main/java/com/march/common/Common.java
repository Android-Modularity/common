package com.march.common;

import android.app.Application;
import android.content.Context;

import com.march.common.adapter.ImgLoadAdapter;
import com.march.common.adapter.JsonAdapter;
import com.march.common.extensions.SizeX;
import com.march.common.model.AppBuildConfig;
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

    private static Common sInst;

    public static Common getInst() {
        if (sInst == null) {
            synchronized (Common.class) {
                if (sInst == null) {
                    sInst = new Common();
                }
            }
        }
        return sInst;
    }

    private Common() {

    }

    private WeakContext    mWeakContext;
    private ImgLoadAdapter mImgLoadAdapter;
    private JsonAdapter    mJsonAdapter;
    private AppBuildConfig mAppBuildConfig;

    public static void init(Application app, Class buildCls) {
        getInst().mWeakContext = new WeakContext(app);
        getInst().mAppBuildConfig = new AppBuildConfig(buildCls);
        DimensUtils.init();
        SizeX.init();
        ToastUtils.init(new ToastUtils.Config());
        PathUtils.init(app, getInst().mAppBuildConfig.APPLICATION_ID);
    }

    public Context getContext() {
        return mWeakContext.get();
    }

    public AppBuildConfig getBuildConfig() {
        return mAppBuildConfig;
    }

    public ImgLoadAdapter getImgLoadAdapter() {
        return mImgLoadAdapter;
    }

    public void setImgLoadAdapter(ImgLoadAdapter imgLoadAdapter) {
        mImgLoadAdapter = imgLoadAdapter;
    }

    public JsonAdapter getJsonAdapter() {
        return mJsonAdapter;
    }

    public void setJsonAdapter(JsonAdapter jsonAdapter) {
        mJsonAdapter = jsonAdapter;
    }
}
