package com.march.common.function;

/**
 * CreateAt : 2017/11/9
 * Describe : T转R
 *
 * @author chendong
 */
public interface Function<T, R> {
    R apply(T t);
}
