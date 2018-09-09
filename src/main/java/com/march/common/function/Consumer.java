package com.march.common.function;

/**
 * CreateAt : 2017/11/9
 * Describe : 消费 T 类型数据
 *
 * @author chendong
 */
public interface Consumer<T> {
    void accept(T t);
}
