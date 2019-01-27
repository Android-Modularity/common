package com.march.common.x;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.march.common.Common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * CreateAt : 2019/1/27
 * Describe :
 *
 * @author chendong
 */
public class SharePreferenceX {

    public static final int MMKV   = 0;
    public static final int SP     = 1;
    public static final int MEMORY = 2;

    public static SharedPreferences get(Context context, int strategy) {
        switch (strategy) {
            case MMKV:
                return com.tencent.mmkv.MMKV.defaultMMKV();
            case SP:
                return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
            case MEMORY:
                return new MemorySharePreference();
        }
        return null;
    }

    public static SharedPreferences get(Context context) {
        switch (Common.exports.spMode) {
            case MMKV:
                return com.tencent.mmkv.MMKV.defaultMMKV();
            case SP:
                return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
            case MEMORY:
                break;
        }
        return null;
    }

    public static class MemorySharePreference implements SharedPreferences {

        private Map<String, Object> map = new HashMap<>();

        @Override
        public Map<String, ?> getAll() {
            return map;
        }

        @Nullable
        @Override
        public String getString(String key, @Nullable String defValue) {
            Object o = map.get(key);
            if (o instanceof String) {
                return (String) o;
            }
            return defValue;
        }


        @Nullable
        @Override
        public Set<String> getStringSet(String key, @Nullable Set<String> defValues) {
            Object o = map.get(key);
            if (o instanceof Set) {
                return (Set<String>) o;
            }
            return defValues;
        }

        @Override
        public int getInt(String key, int defValue) {
            Object o = map.get(key);
            if (o instanceof Integer) {
                return (int) o;
            }
            return defValue;
        }

        @Override
        public long getLong(String key, long defValue) {
            Object o = map.get(key);
            if (o instanceof Long) {
                return (long) o;
            }
            return defValue;
        }

        @Override
        public float getFloat(String key, float defValue) {
            Object o = map.get(key);
            if (o instanceof Float) {
                return (float) o;
            }
            return defValue;
        }

        @Override
        public boolean getBoolean(String key, boolean defValue) {
            Object o = map.get(key);
            if (o instanceof Boolean) {
                return (boolean) o;
            }
            return defValue;
        }

        @Override
        public boolean contains(String key) {
            return map.containsKey(key);
        }

        @Override
        public Editor edit() {
            return null;
        }

        class EditorImpl implements Editor {

            @Override
            public Editor putString(String key, @Nullable String value) {
                if (value != null) {
                    map.put(key, value);
                }
                return this;
            }

            @Override
            public Editor putStringSet(String key, @Nullable Set<String> values) {
                if (values != null) {
                    map.put(key, values);
                }
                return this;
            }

            @Override
            public Editor putInt(String key, int value) {
                map.put(key, value);
                return this;
            }

            @Override
            public Editor putLong(String key, long value) {
                map.put(key, value);
                return this;
            }

            @Override
            public Editor putFloat(String key, float value) {
                map.put(key, value);
                return this;
            }

            @Override
            public Editor putBoolean(String key, boolean value) {
                map.put(key, value);
                return this;
            }

            @Override
            public Editor remove(String key) {
                map.remove(key);
                return this;
            }

            @Override
            public Editor clear() {
                map.clear();
                return this;
            }

            @Override
            public boolean commit() {
                return true;
            }

            @Override
            public void apply() {

            }
        }

        @Override
        public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {

        }

        @Override
        public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {

        }
    }
}
