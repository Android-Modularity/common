package com.march.common.workflow;

/**
 * CreateAt : 2018/8/8
 * Describe :
 *
 * @author chendong
 */
public interface ITask {

    ITask then(ThenAction continueAction);

}
