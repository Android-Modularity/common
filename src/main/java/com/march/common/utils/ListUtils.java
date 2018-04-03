package com.march.common.utils;

import com.march.common.funcs.Function;
import com.march.common.funcs.Predicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * CreateAt : 2018/2/28
 * Describe :
 *
 * @author chendong
 */
public class ListUtils {
    /**
     * 从列表中过滤指定元素
     *
     * @param srcList   源列表
     * @param predicate func
     * @param <T>       范型
     * @return 结果列表
     */
    public static <T> List<T> filter(List<T> srcList, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T t : srcList) {
            if (predicate.test(t)) {
                result.add(t);
            }
        }
        return result;
    }

    /**
     * map 操作
     *
     * @param srcList  源列表
     * @param function func
     * @param <T>      范型
     * @param <R>      范型
     * @return 结果列表
     */
    public static <T, R> List<R> map(List<T> srcList, Function<T, R> function) {
        List<R> result = new ArrayList<>();
        R r;
        for (T t : srcList) {
            r = function.apply(t);
            if (r != null) {
                result.add(r);
            }
        }
        return result;
    }


    /**
     * 构建list
     *
     * @param ts  不定长参数
     * @param <T> 范型
     * @return list
     */
    public static <T> List<T> listOf(T... ts) {
        return Arrays.asList(ts);
    }

    public static <T> List<T> range(int count, Function<Integer, T> map) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            T apply = map.apply(i);
            if (apply != null) {
                list.add(apply);
            }
        }
        return list;
    }

    public static <T> void addOrRemoveForContains(List<T> list, T data) {
        if (list.contains(data)) {
            list.remove(data);
        } else {
            list.add(data);
        }
    }
}