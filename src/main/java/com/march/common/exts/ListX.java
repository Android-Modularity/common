package com.march.common.exts;

import com.march.common.funcs.Consumer;
import com.march.common.funcs.Function;
import com.march.common.funcs.Predicate;

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


    /**
     * 对列表中的每一项执行操作
     *
     * @param srcList  数据源
     * @param consumer 操作
     * @param <T>      范型
     */
    public static <T> void foreach(List<T> srcList, Consumer<T> consumer) {
        if (EmptyX.isEmpty(srcList)) {
            return;
        }
        for (T t : srcList) {
            consumer.accept(t);
        }
    }

    /**
     * 过滤新的列表
     *
     * @param srcList   原始数据
     * @param predicate 规则函数
     * @param <T>       范型
     * @return 过滤后的列表
     */
    public static <T> List<T> filter(List<T> srcList, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        if (EmptyX.isEmpty(srcList)) {
            return result;
        }
        for (T t : srcList) {
            if (predicate.test(t)) {
                result.add(t);
            }
        }
        return result;
    }

    /**
     * @param srcList  原始数据
     * @param function 转换函数
     * @param <T>      原始类型
     * @param <R>      目标类型
     * @return 转换数据类型生成新的列表
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
     * @param ts  原数据
     * @param <T> 范型
     * @return 生成列表
     */
    public static <T> List<T> listOf(T... ts) {
        if (ts == null || ts.length == 0) {
            return new ArrayList<>();
        }
        return Arrays.asList(ts);
    }

    /**
     * @param ts 原数据
     * @return 生成 int 类型列表
     */
    public static List<Integer> intListOf(int... ts) {
        List<Integer> integers = new ArrayList<>();
        for (int t : ts) {
            integers.add(t);
        }
        return integers;
    }


    /**
     * @param count 元素个数
     * @param map   转换函数
     * @param <T>   范型
     * @return 根据数字队列生成列表
     */
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

    /**
     * 存在删除，不存在添加
     *
     * @param list 列表
     * @param data 数据
     * @param <T>  范型
     */
    public static <T> void toggleItem(List<T> list, T data) {
        if (list.contains(data)) {
            list.remove(data);
        } else {
            list.add(data);
        }
    }

    /**
     * @param list      列表
     * @param predicate 检测函数
     * @param <T>       范型
     * @return 全部满足要求，返回 true，否则 false
     */
    public static <T> boolean all(List<T> list, Predicate<T> predicate) {
        for (T t : list) {
            if (!predicate.test(t)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param list      数据源
     * @param predicate 检测函数
     * @param <T>       范型
     * @return 任何一个满足要求，返回 true，否则 false
     */
    public static <T> boolean any(List<T> list, Predicate<T> predicate) {
        for (T t : list) {
            if (predicate.test(t)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 根据表达式查找坐标
     *
     * @param list      数据集合
     * @param predicate
     * @param <T>
     * @return
     */
    public static <T> int indexOf(List<T> list, Predicate<T> predicate) {
        for (int i = 0; i < list.size(); i++) {
            if (predicate.test(list.get(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 查找符合规则的一项
     *
     * @param list      数据源
     * @param predicate 测试
     * @param <T>       范型
     * @return 查找到的
     */
    public static <T> T find(List<T> list, Predicate<T> predicate) {
        for (T t : list) {
            if (predicate.test(t)) {
                return t;
            }
        }
        return null;
    }


    /**
     * 从后面查找符合规则的一项
     *
     * @param list      数据源
     * @param predicate 测试
     * @param <T>       范型
     * @return 查找到的
     */
    public static <T> T findLast(List<T> list, Predicate<T> predicate) {
        int start = list.size() - 1;
        for (int i = start; i >= 0; i--) {
            if (predicate.test(list.get(i))) {
                return list.get(i);
            }
        }
        return null;
    }

    /**
     * @param list 数据源
     * @param sign 间隔符
     * @param <T>  范型
     * @return 列表调用 toString 拼接成字符串
     */
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

    /**
     * @param list 数据源
     * @param sign 间隔符
     * @param <T>  范型
     * @return 列表调用 toString 拼接成字符串
     */
    public static <T> String join2String(List<T> list, String sign, Function<T, String> mapper) {
        StringBuilder builder = new StringBuilder();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            builder.append(mapper.apply(list.get(i)));
            if (i != size - 1) {
                builder.append(sign);
            }
        }
        return builder.toString();
    }


    /**
     * @param list      数据源
     * @param predicate 规则
     * @param <T>       范型
     * @return 符合条件的元素个数
     */
    public static <T> int count(List<T> list, Predicate<T> predicate) {
        int count = 0;
        for (T t : list) {
            if (predicate.test(t)) {
                count++;
            }
        }
        return count;
    }
}
