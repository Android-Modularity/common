package com.march.common.workflow;

/**
 * CreateAt : 2018/8/8
 * Describe : 继续操作任务接口
 *
 * @author chendong
 */
public interface ThenAction {
    Object call(Object lastResult, Exception error);
}
