package com.march.common.mgrs;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.SparseArray;

import com.march.common.Common;
import com.march.common.exts.JsonX;
import com.tencent.mmkv.MMKV;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * CreateAt : 2018/9/24
 * Describe :
 *
 * @author chendong
 */
public class KVMgr implements IMgr {

    public static final int STRATEGY_MMKV = 0;
    public static final int STRATEGY_SP = 1;
    public static final int STRATEGY_MEMORY = 2;
    private static SparseArray<IKV> sIKVMap = new SparseArray<>();

    private KVMgr() {

    }

    /**
     * 获取一个 key-value 操作类
     *
     * @return IKV
     */
    public static IKV getInst() {
        return getInst(Common.exports.kvStrategy);
    }

    /**
     * 以指定 strategy 获取 key-value 操作类
     *
     * @param strategy 策略
     * @return IKV
     */
    public static IKV getInst(int strategy) {
        IKV ikv = sIKVMap.get(strategy);
        if (ikv != null) {
            return ikv;
        }
        switch (strategy) {
            case STRATEGY_MMKV:
                ikv = new MMKVImpl(Common.app());
                break;
            case STRATEGY_SP:
                ikv = new SpKvImpl(Common.app());
                break;
            case STRATEGY_MEMORY:
                ikv = new MemoryKvImpl();
                break;
        }
        sIKVMap.put(strategy, ikv);
        return ikv;
    }

    /**
     * @return 获取 mmkv key-value 操作
     */
    public static IKV mmkv() {
        return getInst(STRATEGY_MMKV);
    }

    /**
     * @return 获取 SharePreference key-value 操作
     */
    public static IKV sp() {
        return getInst(STRATEGY_SP);
    }

    /**
     * @return 获取内存 key-value 操作
     */
    public static IKV memory() {
        return getInst(STRATEGY_MEMORY);
    }

    @Override
    public void recycle() {
        sIKVMap.clear();
    }

    @Override
    public boolean isRecycled() {
        return false;
    }

    // key-value 操作接口
    public interface IKV {

        /*
        int
         */
        int getInt(String key);

        int getInt(String key, int defaultValue);

        IKV putInt(String key, int value);

        /*
        float
         */

        float getFloat(String key);

        float getFloat(String key, float defaultValue);

        IKV putFloat(String key, float value);

        /*
        long
         */
        long getLong(String key);

        long getLong(String key, long defaultValue);

        IKV putLong(String key, long value);

        /*
        boolean
         */
        boolean getBool(String key);

        boolean getBool(String key, boolean defaultValue);

        IKV putBool(String key, boolean value);

        /*
        String
         */
        String getString(String key);


        String getString(String key, String defaultValue);

        IKV putString(String key, String value);

        /*
        String sets
         */
        Set<String> getStringSets(String key);

        IKV putStringSets(String key, Set<String> value);

        /*
        obj list map
         */
        <T> T getObj(String key, Class<T> clazz);

        IKV putObj(String key, Object value);

        <T> List<T> getList(String key, Class<T> clazz);

        <K, V> Map<K, V> getMap(String key, Class<K> kClazz, Class<V> vClazz);
    }

    // 基于 Tencent MMKV 的实现
    static class MMKVImpl implements IKV {

        MMKVImpl(Context context) {
            MMKV.initialize(context);
        }

        private MMKV kv() {
            return MMKV.defaultMMKV();
        }

        @Override
        public int getInt(String key, int defaultValue) {
            return kv().getInt(key, defaultValue);
        }

        @Override
        public float getFloat(String key, float defaultValue) {
            return kv().getFloat(key, defaultValue);
        }

        @Override
        public long getLong(String key, long defaultValue) {
            return kv().getLong(key, defaultValue);
        }

        @Override
        public boolean getBool(String key, boolean defaultValue) {
            return kv().getBoolean(key, defaultValue);
        }

        @Override
        public String getString(String key, String defaultValue) {
            return kv().getString(key, defaultValue);
        }

        @Override
        public Set<String> getStringSets(String key) {
            HashSet<String> strings = new HashSet<>();
            return kv().getStringSet(key, strings);
        }

        @Override
        public int getInt(String key) {
            return getInt(key, 0);
        }

        @Override
        public float getFloat(String key) {
            return getFloat(key, 0);
        }

        @Override
        public long getLong(String key) {
            return getLong(key, 0);
        }

        @Override
        public boolean getBool(String key) {
            return getBool(key, false);
        }

        @Override
        public String getString(String key) {
            return getString(key, "");
        }

        @Override
        public <T> T getObj(String key, Class<T> clazz) {
            return JsonX.toObj(getString(key), clazz);
        }

        @Override
        public <T> List<T> getList(String key, Class<T> clazz) {
            return JsonX.toList(getString(key), clazz);
        }

        @Override
        public <K, V> Map<K, V> getMap(String key, Class<K> kClazz, Class<V> vClazz) {
            return JsonX.toMap(getString(key), kClazz, vClazz);
        }

        @Override
        public IKV putInt(String key, int value) {
            kv().putInt(key, value);
            return this;
        }

        @Override
        public IKV putFloat(String key, float value) {
            kv().putFloat(key, value);
            return this;
        }

        @Override
        public IKV putLong(String key, long value) {
            kv().putLong(key, value);
            return this;
        }

        @Override
        public IKV putBool(String key, boolean value) {
            kv().putBoolean(key, value);
            return this;
        }

        @Override
        public IKV putString(String key, String value) {
            kv().putString(key, value);
            return this;
        }

        @Override
        public IKV putStringSets(String key, Set<String> value) {
            kv().putStringSet(key, value);
            return this;
        }

        @Override
        public IKV putObj(String key, Object value) {
            kv().putString(key, JsonX.toJson(value));
            return this;
        }

    }

    // 基于 SharePreference 实现
    static class SpKvImpl implements IKV {

        private final SharedPreferences mSp;

        public SpKvImpl(Context context) {
            mSp = context.getSharedPreferences(getClass().getSimpleName(), Context.MODE_PRIVATE);
        }

        @Override
        public int getInt(String key) {
            return getInt(key, 0);
        }

        @Override
        public int getInt(String key, int defaultValue) {
            return mSp.getInt(key, defaultValue);
        }

        @Override
        public IKV putInt(String key, int value) {
            mSp.edit().putInt(key, value).apply();
            return this;
        }

        @Override
        public float getFloat(String key) {
            return getFloat(key, 0);
        }

        @Override
        public float getFloat(String key, float defaultValue) {
            return mSp.getFloat(key, defaultValue);
        }

        @Override
        public IKV putFloat(String key, float value) {
            mSp.edit().putFloat(key, value).apply();
            return this;
        }

        @Override
        public long getLong(String key) {
            return getLong(key, 0);
        }

        @Override
        public long getLong(String key, long defaultValue) {
            return mSp.getLong(key, defaultValue);
        }

        @Override
        public IKV putLong(String key, long value) {
            mSp.edit().putLong(key, value).apply();
            return this;
        }

        @Override
        public boolean getBool(String key) {
            return getBool(key, false);
        }

        @Override
        public boolean getBool(String key, boolean defaultValue) {
            return mSp.getBoolean(key, defaultValue);
        }

        @Override
        public IKV putBool(String key, boolean value) {
            mSp.edit().putBoolean(key, value).apply();
            return this;
        }

        @Override
        public String getString(String key) {
            return getString(key, "");
        }

        @Override
        public String getString(String key, String defaultValue) {
            return mSp.getString(key, defaultValue);
        }

        @Override
        public IKV putString(String key, String value) {
            mSp.edit().putString(key, value).apply();
            return this;
        }

        @Override
        public Set<String> getStringSets(String key) {
            return mSp.getStringSet(key, new HashSet<>());
        }

        @Override
        public IKV putStringSets(String key, Set<String> value) {
            mSp.edit().putStringSet(key, value).apply();
            return this;
        }

        @Override
        public <T> T getObj(String key, Class<T> clazz) {
            return JsonX.toObj(getString(key), clazz);
        }

        @Override
        public IKV putObj(String key, Object value) {
            return putString(key, JsonX.toJson(value));
        }

        @Override
        public <T> List<T> getList(String key, Class<T> clazz) {
            return JsonX.toList(getString(key), clazz);
        }

        @Override
        public <K, V> Map<K, V> getMap(String key, Class<K> kClazz, Class<V> vClazz) {
            return JsonX.toMap(getString(key), kClazz, vClazz);
        }
    }

    // 基于内存实现
    static class MemoryKvImpl implements IKV {

        private Map<String, Object> mObjectMap;

        public MemoryKvImpl() {
            mObjectMap = new HashMap<>();
        }

        @Override
        public int getInt(String key) {
            return getInt(key, 0);
        }

        @Override
        public int getInt(String key, int defaultValue) {
            Object o = mObjectMap.get(key);
            if (o != null && o instanceof Integer) {
                return (int) o;
            }
            return defaultValue;
        }

        @Override
        public IKV putInt(String key, int value) {
            mObjectMap.put(key, value);
            return this;
        }

        @Override
        public float getFloat(String key) {
            return getFloat(key, 0);
        }

        @Override
        public float getFloat(String key, float defaultValue) {
            Object o = mObjectMap.get(key);
            if (o != null && o instanceof Float) {
                return (float) o;
            }
            return defaultValue;
        }

        @Override
        public IKV putFloat(String key, float value) {
            mObjectMap.put(key, value);
            return this;
        }

        @Override
        public long getLong(String key) {
            return getLong(key, 0);
        }

        @Override
        public long getLong(String key, long defaultValue) {
            Object o = mObjectMap.get(key);
            if (o != null && o instanceof Long) {
                return (long) o;
            }
            return defaultValue;
        }

        @Override
        public IKV putLong(String key, long value) {
            mObjectMap.put(key, value);
            return this;
        }

        @Override
        public boolean getBool(String key) {
            return getBool(key, false);
        }

        @Override
        public boolean getBool(String key, boolean defaultValue) {
            Object o = mObjectMap.get(key);
            if (o != null && o instanceof Boolean) {
                return (boolean) o;
            }
            return defaultValue;
        }

        @Override
        public IKV putBool(String key, boolean value) {
            mObjectMap.put(key, value);
            return this;
        }

        @Override
        public String getString(String key) {
            return getString(key, "");
        }

        @Override
        public String getString(String key, String defaultValue) {
            Object o = mObjectMap.get(key);
            if (o != null && o instanceof String) {
                return (String) o;
            }
            return defaultValue;
        }

        @Override
        public IKV putString(String key, String value) {
            mObjectMap.put(key, value);
            return this;
        }

        @Override
        public Set<String> getStringSets(String key) {
            Object o = mObjectMap.get(key);
            if (o != null && o instanceof Set) {
                return (Set<String>) o;
            }
            return new HashSet<>();
        }

        @Override
        public IKV putStringSets(String key, Set<String> value) {
            mObjectMap.put(key, value);
            return this;
        }

        @Override
        public <T> T getObj(String key, Class<T> clazz) {
            Object o = mObjectMap.get(key);
            if (o != null && o.getClass().equals(clazz)) {
                return (T) o;
            }
            return null;
        }

        @Override
        public IKV putObj(String key, Object value) {
            mObjectMap.put(key, value);
            return this;
        }

        @Override
        public <T> List<T> getList(String key, Class<T> clazz) {
            Object o = mObjectMap.get(key);
            if (o != null && o instanceof List) {
                return (List<T>) o;
            }
            return null;
        }

        @Override
        public <K, V> Map<K, V> getMap(String key, Class<K> kClazz, Class<V> vClazz) {
            Object o = mObjectMap.get(key);
            if (o != null && o instanceof Map) {
                return (Map<K, V>) o;
            }
            return null;
        }
    }
}
