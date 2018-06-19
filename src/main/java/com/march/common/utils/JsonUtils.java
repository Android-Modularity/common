package com.march.common.utils;

import android.text.TextUtils;

import com.march.common.Common;
import com.march.common.adapter.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public static String toJsonString(String json,String def){
        try {
            if(json.startsWith("[")) {
                return new JSONArray(json).toString(2).replace("\\/", "/");
            } else if(json.startsWith("{")){
                return new JSONObject(json).toString(2).replace("\\/", "/");
            }
        } catch (JSONException e) {
            // e.printStackTrace();
            return def;
        }
        return def;
    }
}
