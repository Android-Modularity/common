package com.march.common.funcs;

/**
 * CreateAt : 2017/11/9
 * Describe : 对 T 类型数据进行判断
 *
 * @author chendong
 */
public interface Predicate<T> {
    boolean test(T t);
}
