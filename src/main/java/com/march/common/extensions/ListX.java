package com.march.common.extensions;

import com.march.common.function.Function;
import com.march.common.function.Predicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * CreateAt : 2018/8/2
 * Describe :
 *
 * @author chendong
 */
public class ListX {

    // 过滤新的列表
    public static <T> List<T> filter(List<T> srcList, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T t : srcList) {
            if (predicate.test(t)) {
                result.add(t);
            }
        }
        return result;
    }

    // 根据规则生成新的列表
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

    // 生成列表
    public static <T> List<T> listOf(T... ts) {
        return Arrays.asList(ts);
    }

    // 生成 int 类型列表
    public static  List<Integer> intListOf(int... ts) {
        List<Integer> integers = new ArrayList<>();
        for (int t : ts) {
            integers.add(t);
        }
        return integers;
    }


    // 根据数字队列生成列表
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

    // 存在删除，不存在添加
    public static <T> void toggleItem(List<T> list, T data) {
        if (list.contains(data)) {
            list.remove(data);
        } else {
            list.add(data);
        }
    }

    // 全部满足要求，返回 true，否则 false
    public static <T> boolean all(List<T> list, Predicate<T> predicate) {
        for (T t : list) {
            if(!predicate.test(t)) {
                return false;
            }
        }
        return true;
    }

    // 任何一个满足要求，返回 true，否则 false
    public static <T> boolean any(List<T> list, Predicate<T> predicate) {
        for (T t : list) {
            if(predicate.test(t)) {
                return true;
            }
        }
        return false;
    }

    public static <T> String join2String(List<T> list, String sign) {
        StringBuilder builder = new StringBuilder();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            builder.append(list.get(i).toString());
            if (i != size - 1) {
                builder.append(sign);
            }
        }
        return builder.toString();
    }
}
