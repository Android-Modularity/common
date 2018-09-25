package com.march.common.pool;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * CreateAt : 2017/12/29
 * Describe : 内存 map 管理
 *
 * @author chendong
 */
public class MemoryKVManager {

    private static MemoryKVManager sInst;

    public static MemoryKVManager getInst() {
        if (sInst == null) {
            synchronized (MemoryKVManager.class) {
                if (sInst == null) {
                    sInst = new MemoryKVManager();
                }
            }
        }
        return sInst;
    }

    private MemoryKVManager() {
        mCacheMap = new LinkedHashMap<>();
    }

    private Map<String, Object> mCacheMap;

    public void put(String key, Object value) {
        mCacheMap.put(key, value);
    }

    public <T> T get(String key, T def) {
        if (!mCacheMap.containsKey(key)) {
            return def;
        }
        Object obj = mCacheMap.get(key);
        if (obj != null) {
            return (T) obj;
        }
        return def;
    }
}
