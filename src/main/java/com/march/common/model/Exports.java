package com.march.common.model;

import android.app.Application;

import com.march.common.adapter.ImgLoadAdapter;
import com.march.common.adapter.JsonAdapter;
import com.march.common.mgrs.KVMgr;
import com.march.common.x.SharedPreferencesX;

/**
 * CreateAt : 2018/9/9
 * Describe :
 *
 * @author chendong
 */
public class Exports {

    public boolean        init;

    public Application    app;
    public ImgLoadAdapter imageLoader;
    public JsonAdapter    jsonParser;
    public AppBuildConfig appConfig;

    public int kvStrategy = KVMgr.STRATEGY_MMKV;
    public int spMode     = SharedPreferencesX.MMKV;
}
