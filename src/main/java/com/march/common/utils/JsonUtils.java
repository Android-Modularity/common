package com.march.common.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * CreateAt : 2018/1/12
 * Describe :
 *
 * @author chendong
 */
public class JsonUtils {

    public static Gson sGson = new Gson();

    public static String toJson(Object object) {
        return sGson.toJson(object);
    }

    public static <T> T toObj(String json, Class<T> cls) {
        return sGson.fromJson(json, cls);
    }

    public static <T> List<T> toList(String json) {
        return sGson.fromJson(json, new TypeToken<List<T>>() {
        }.getType());
    }

    public static <K, V> Map<K, V> toMap(String json) {
        return sGson.fromJson(json, new TypeToken<Map<K, V>>() {
        }.getType());
    }
}
