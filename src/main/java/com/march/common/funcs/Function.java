package com.march.common.funcs;

/**
 * CreateAt : 2017/11/9
 * Describe : T转R
 *
 * @author chendong
 */
public interface Function<T, R> {
    R apply(T t);
}
