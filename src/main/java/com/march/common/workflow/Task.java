package com.march.common.workflow;

import com.march.common.pool.ExecutorsPool;

/**
 * CreateAt : 2018/8/8
 * Describe :
 *
 * @author chendong
 */
public class Task implements ITask {


    boolean finished;

    Object result;

    public Task() {

    }

    private ThenAction continueAction;

    @Override
    public ITask then(ThenAction thenAction) {
        Task task = new Task();
        this.continueAction = thenAction;
        if (finished) {
            task.result = thenAction.call(result, null);
            finish();
            task.finish();
        }
        return task;
    }

    public static ITask call(CallAction callAction) {
        Task task = new Task();
        ExecutorsPool.getInst().ui().post(new Runnable() {
            @Override
            public void run() {
                task.result = callAction.call();
                task.finish();
                task.finished = true;
            }
        });

        return task;
    }

    public void runThen() {
        ExecutorsPool.getInst().ui().post(new Runnable() {
            @Override
            public void run() {
                if (continueAction != null) {
                    continueAction.call(result, null);
                }
            }
        });
    }

    public void finish() {
        finished = true;
        runThen();
    }
}
