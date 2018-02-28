package com.march.common.model;

import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * CreateAt : 2018/1/12
 * Describe :
 *
 * @author chendong
 */
public class WeakContext extends WeakReference<Context> {

    public WeakContext(Context referent) {
        super(referent);
    }
}
