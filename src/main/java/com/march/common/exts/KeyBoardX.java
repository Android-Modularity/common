package com.march.common.exts;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.march.common.Common;
import com.march.common.funcs.Consumer;
import com.march.common.funcs.Predicate;

/**
 * CreateAt : 2018/9/28
 * Describe : 键盘¬
 *
 * @author chendong
 */
public class KeyBoardX {

    public static final int CHECK_HEIGHT = 200;

    /**
     * 获取 manager
     *
     * @param context ctx
     * @return InputMethodManager
     */
    public static InputMethodManager getManager(Context context) {
        return (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
    }

    /**
     * 显示键盘
     *
     * @param activity act
     */
    public static void showSoftInput(final Activity activity) {
        InputMethodManager imm = getManager(activity);
        if (imm == null) return;
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.requestFocus();
        }
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 显示键盘
     *
     * @param view view
     */
    public static void showSoftInput(final View view) {
        InputMethodManager imm = getManager(Common.app());
        if (imm == null) return;
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 隐藏键盘
     *
     * @param activity act
     */
    public static void hideSoftInput(final Activity activity) {
        InputMethodManager imm = getManager(activity);
        if (imm == null) return;
        View view = activity.getCurrentFocus();
        if (view == null) view = new View(activity);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 隐藏键盘
     *
     * @param view view
     */
    public static void hideSoftInput(final View view) {
        InputMethodManager imm = getManager(Common.app());
        if (imm == null) return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 切换键盘
     */
    public static void toggleSoftInput() {
        InputMethodManager imm = getManager(Common.app());
        if (imm == null) return;
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * @param activity act
     * @return 键盘是否显示
     */
    public static boolean isSoftInputVisible(final Activity activity) {
        return isSoftInputVisible(activity, 200);
    }

    /**
     * @param activity             act
     * @param minHeightOfSoftInput 最小宽度
     * @return 键盘是否显示
     */
    public static boolean isSoftInputVisible(final Activity activity,
            final int minHeightOfSoftInput) {
        return getContentViewInvisibleHeight(activity) >= minHeightOfSoftInput;
    }


    /**
     * 检测键盘改变
     *
     * @param tokenView    容器的 View
     * @param isWatching   是否在观察
     * @param hideConsumer 隐藏返回 true, 显示返回 false
     */
    public static void watchKeyBoardChanged(View tokenView, int height,
            Predicate<Void> isWatching, Consumer<Boolean> hideConsumer) {
        tokenView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            if (isWatching != null && !isWatching.test(null)) {
                return;
            }
            Rect r = new Rect();
            tokenView.getWindowVisibleDisplayFrame(r);
            int screenHeight = tokenView.getRootView().getHeight();
            int heightDifference = screenHeight - (r.bottom);
            if (heightDifference < height) {
                hideConsumer.accept(true);
            } else {
                hideConsumer.accept(false);
            }
        });
    }

    private static int getContentViewInvisibleHeight(final Activity activity) {
        final FrameLayout contentView = activity.findViewById(android.R.id.content);
        final View contentViewChild = contentView.getChildAt(0);
        final Rect outRect = new Rect();
        contentViewChild.getWindowVisibleDisplayFrame(outRect);
        return contentViewChild.getBottom() - outRect.bottom;
    }
}
