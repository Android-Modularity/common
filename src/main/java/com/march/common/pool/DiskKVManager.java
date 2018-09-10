package com.march.common.pool;

import android.content.Context;
import android.content.SharedPreferences;

import com.march.common.Common;
import com.march.common.extensions.EmptyX;
import com.march.common.extensions.JsonX;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * CreateAt : 2017/12/29
 * Describe : 磁盘 map 管理
 *
 * @author chendong
 */
public class DiskKVManager {

    private static DiskKVManager     sInst;
    private final  SharedPreferences mPreferences;

    public static DiskKVManager getInst() {
        if (sInst == null) {
            synchronized (DiskKVManager.class) {
                if (sInst == null) {
                    sInst = new DiskKVManager();
                }
            }
        }
        return sInst;
    }

    private DiskKVManager() {
        mPreferences = Common.app().getSharedPreferences(getClass().getSimpleName(), Context.MODE_PRIVATE);
    }

    public void put(final String key, final Object value) {
        MemoryKVManager.getInst().put(key, value);
        ExecutorsPool.getInst().execute(new Runnable() {
            @Override
            public void run() {
                SharedPreferences.Editor editor = mPreferences.edit();
                if (value instanceof Integer) {
                    editor.putInt(key, (Integer) value);
                } else if (value instanceof Float) {
                    editor.putFloat(key, (Float) value);
                } else if (value instanceof Boolean) {
                    editor.putBoolean(key, (Boolean) value);
                } else if (value instanceof Long) {
                    editor.putLong(key, (Long) value);
                } else if (value instanceof String) {
                    editor.putString(key, (String) value);
                } else if (value instanceof Set) {
                    editor.putStringSet(key, (Set<String>) value);
                } else {
                    String json = JsonX.toJson(value);
                    if (!EmptyX.isEmpty(json)) {
                        editor.putString(key, json);
                    }
                }
                editor.apply();
            }
        });
    }

    public <T> T get(String key, T def) {
        Object memoryCache = MemoryKVManager.getInst().get(key, null);
        if (memoryCache != null) {
            return (T) memoryCache;
        }
        Object result = def;
        if (def instanceof Integer) {
            result = mPreferences.getInt(key, (Integer) def);
        } else if (def instanceof Float) {
            result = mPreferences.getFloat(key, (Float) def);
        } else if (def instanceof Boolean) {
            result = mPreferences.getBoolean(key, (Boolean) def);
        } else if (def instanceof Long) {
            result = mPreferences.getLong(key, (Long) def);
        } else if (def instanceof String) {
            result = mPreferences.getString(key, (String) def);
        } else if (def instanceof Set) {
            result = mPreferences.getStringSet(key, (Set<String>) def);
        }
        return (T) result;
    }

    public <T> T getObj(String key, Class<T> clazz, T def) {
        Object memoryCache = MemoryKVManager.getInst().get(key, null);
        if (memoryCache != null && memoryCache.getClass() == clazz) {
            return (T) memoryCache;
        }
        T result = null;
        String json = mPreferences.getString(key, "");
        if (!EmptyX.isEmpty(json)) {
            result = JsonX.toObj(json, clazz);
        }
        if (result == null) {
            return def;
        }
        return result;
    }

    public <T> List<T> getList(String key, Class<T> clazz, List<T> def) {
        Object memoryCache = MemoryKVManager.getInst().get(key, null);
        if (memoryCache != null && memoryCache instanceof List) {
            return (List<T>) memoryCache;
        }
        List<T> result = null;
        String json = mPreferences.getString(key, "");
        if (!EmptyX.isEmpty(json)) {
            result = JsonX.toList(json, clazz);
        }
        if (result == null) {
            return def;
        }
        return result;
    }


    public <K, V> Map<K, V> getMap(String key, Class<K> kClazz, Class<V> vClazz, Map<K, V> def) {
        Object memoryCache = MemoryKVManager.getInst().get(key, null);
        if (memoryCache != null && memoryCache instanceof Map) {
            return (Map<K, V>) memoryCache;
        }
        Map<K, V> result = null;
        String json = mPreferences.getString(key, "");
        if (!EmptyX.isEmpty(json)) {
            result = JsonX.toMap(json, kClazz, vClazz);
        }
        if (result == null) {
            return def;
        }
        return result;
    }

}
