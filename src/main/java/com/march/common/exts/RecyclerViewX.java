package com.march.common.exts;

import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;

/**
 * CreateAt : 2018/9/28
 * Describe :
 *
 * @author chendong
 */
public class RecyclerViewX {

    /**
     * 滑动到顶部
     *
     * @param recyclerView rv
     * @param pos          pos
     */
    public static void smoothScrollPositionStart(RecyclerView recyclerView, final int pos) {
        LinearSmoothScroller smoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }

            @Override
            protected int getHorizontalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
        smoothScroller.setTargetPosition(pos);
        recyclerView.getLayoutManager().startSmoothScroll(smoothScroller);
    }

    /**
     * 滑动到底部
     *
     * @param recyclerView rv
     * @param pos          pos
     */
    public static void smoothScrollPositionEnd(RecyclerView recyclerView, final int pos) {
        LinearSmoothScroller smoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_END;
            }

            @Override
            protected int getHorizontalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_END;
            }
        };
        smoothScroller.setTargetPosition(pos);
        recyclerView.getLayoutManager().startSmoothScroll(smoothScroller);
    }

}
