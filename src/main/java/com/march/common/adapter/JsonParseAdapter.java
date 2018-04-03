package com.march.common.adapter;

import java.util.List;
import java.util.Map;

/**
 * CreateAt : 2018/4/3
 * Describe :
 *
 * @author chendong
 */
public interface JsonParseAdapter {

    String toJson(Object object);

    <T> T toObj(String json, Class<T> cls);

    <T> List<T> toList(String json);

    <K, V> Map<K, V> toMap(String json);
}
