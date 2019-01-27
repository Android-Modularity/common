package com.march.common.x;

import android.text.TextUtils;

import com.march.common.Common;
import com.march.common.adapter.JsonAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * CreateAt : 2018/6/17
 * Describe : Json 解析转换
 *
 * @author chendong
 */
public class JsonX {

    public static String toJson(Object object) {
        if (object == null) return "";
        JsonAdapter jsonAdapter = Common.exports.jsonParser;
        if (jsonAdapter == null) {
            return "";
        }
        return jsonAdapter.toJson(object);
    }

    public static <T> T toObj(String json, Class<T> clazz) {
        if (TextUtils.isEmpty(json) || clazz == null) {
            return null;
        }
        JsonAdapter jsonAdapter = Common.exports.jsonParser;
        if (jsonAdapter == null) {
            return null;
        }
        return jsonAdapter.toObj(json, clazz);
    }

    public static <T> List<T> toList(String json, Class<T> clazz) {
        if (TextUtils.isEmpty(json) || clazz == null) {
            return null;
        }
        JsonAdapter jsonAdapter = Common.exports.jsonParser;
        if (jsonAdapter == null) {
            return null;
        }
        return jsonAdapter.toList(json, clazz);
    }

    public static <K, V> Map<K, V> toMap(String json, Class<K> kClazz, Class<V> vClazz) {
        if (TextUtils.isEmpty(json) || kClazz == null || vClazz == null) {
            return null;
        }
        JsonAdapter jsonAdapter = Common.exports.jsonParser;
        if (jsonAdapter == null) {
            return null;
        }
        return jsonAdapter.toMap(json, kClazz, vClazz);
    }

    public static String toJsonString(String json, String def) {
        try {
            if (json.startsWith("[")) {
                return new JSONArray(json).toString(2).replace("\\/", "/");
            } else if (json.startsWith("{")) {
                return new JSONObject(json).toString(2).replace("\\/", "/");
            }
        } catch (JSONException e) {
            // e.printStackTrace();
            return def;
        }
        return def;
    }
}
