package com.march.common;

import com.march.common.adapter.JsonParser;

/**
 * CreateAt : 2018/6/13
 * Describe :
 *
 * @author chendong
 */
public interface CommonInjector {

    Class getConfigClass();

    JsonParser getJsonParser();
}
