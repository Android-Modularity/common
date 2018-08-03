package com.march.common.extensions;

import android.view.View;


/**
 * CreateAt : 2018/8/3
 * Describe :
 *
 * @author chendong
 */
public class ViewX {

    public static void click(View parent, int id, View.OnClickListener listener) {
        View viewById = parent.findViewById(id);
        if(viewById!=null){
            viewById.setOnClickListener(listener);
        }
    }
}
