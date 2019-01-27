package com.march.common.adapter;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * CreateAt : 2018/4/3
 * Describe :
 *
 * @author chendong
 */
public interface JsonAdapter {

    String toJson(Object object);

    <T> T toObj(String json, Class<T> clazz);

    <T> T toObj(String json, Type type);

    <T> List<T> toList(String json, Class<T> clazz);

    <K, V> Map<K, V> toMap(String json, Class<K> kClazz, Class<V> vClazz);
}
