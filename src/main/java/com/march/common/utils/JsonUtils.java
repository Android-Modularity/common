package com.march.common.utils;

import android.text.TextUtils;

import com.march.common.Common;
import com.march.common.adapter.JsonParser;

import java.util.List;
import java.util.Map;

/**
 * CreateAt : 2018/6/17
 * Describe :
 *
 * @author chendong
 */
public class JsonUtils {

    public static String toJson(Object object) {
        if (object == null) return "";
        JsonParser jsonParser = Common.Injector.getJsonParser();
        if (jsonParser == null) {
            return "";
        }
        return jsonParser.toJson(object);
    }

    public static <T> T toObj(String json, Class<T> clazz) {
        if (TextUtils.isEmpty(json) || clazz == null) {
            return null;
        }
        JsonParser jsonParser = Common.Injector.getJsonParser();
        if (jsonParser == null) {
            return null;
        }
        return jsonParser.toObj(json, clazz);
    }

    public static <T> List<T> toList(String json, Class<T> clazz) {
        if (TextUtils.isEmpty(json) || clazz == null) {
            return null;
        }
        JsonParser jsonParser = Common.Injector.getJsonParser();
        if (jsonParser == null) {
            return null;
        }
        return jsonParser.toList(json, clazz);
    }

    public static <K, V> Map<K, V> toMap(String json, Class<K> kClazz, Class<V> vClazz) {
        if (TextUtils.isEmpty(json) || kClazz == null || vClazz == null) {
            return null;
        }
        JsonParser jsonParser = Common.Injector.getJsonParser();
        if (jsonParser == null) {
            return null;
        }
        return jsonParser.toMap(json, kClazz, vClazz);
    }
}
