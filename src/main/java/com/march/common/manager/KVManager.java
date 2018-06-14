package com.march.common.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.march.common.Common;
import com.march.common.utils.CheckUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * CreateAt : 2017/12/29
 * Describe :
 *
 * @author chendong
 */
public class KVManager {

    private static KVManager sInst;

    public static KVManager getInst() {
        if (sInst == null) {
            synchronized (KVManager.class) {
                if (sInst == null) {
                    sInst = new KVManager();
                }
            }
        }
        return sInst;
    }

    public static final String STORAGE_NAME = "STORAGE_NAME";
    public static final String STORAGE_KEY = "STORAGE_KEY";

    private KVManager() {
        mSharedPreferences = Common.getContext().getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE);
        String json = mSharedPreferences.getString(STORAGE_KEY, "");
        if (CheckUtils.isEmpty(json)) {
            mCacheMap = new HashMap<>();
        } else {
            mCacheMap = Common.Injector.getJsonParser().toMap(json);
        }
    }

    private SharedPreferences mSharedPreferences;
    private Map<String, Object> mCacheMap;

    public void put(String key, Object value) {
        mCacheMap.put(key, value);
        String json = Common.Injector.getJsonParser().toJson(mCacheMap);
        getEditor().putString(STORAGE_KEY, json).apply();
    }

    public <T> T get(String key, T def) {
        Object obj = mCacheMap.get(key);
        if (obj == null) {
            return def;
        } else {
            try {
                return (T) obj;
            } catch (Exception e) {
                return def;
            }
        }
    }

    private SharedPreferences.Editor getEditor() {
        return mSharedPreferences.edit();
    }
}
