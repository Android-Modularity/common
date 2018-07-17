package com.march.common.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.march.common.Common;
import com.march.common.R;


/**
 * CreateAt : 16/8/13
 * Describe : Toast工具类
 *
 * @author chendong
 */
public class ToastUtils {

    private static Handler sHandler;
    private static Config sConfig;
    private static boolean sNoToast;
    private static Toast sToast;

    public static void setNoToast(boolean noToast) {
        sNoToast = noToast;
    }

    public static void init(Config config) {
        sConfig = config;
        sHandler = new Handler(Looper.getMainLooper());
    }

    private static Context getContext() {
        return Common.getContext();
    }

    /**
     * 显示短toast
     *
     * @param msg 信息
     */
    public static void show(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }


    /**
     * 显示短toast
     *
     * @param strRes 字符串资源
     */
    public static void show(int strRes) {
        showToast(getContext().getString(strRes), Toast.LENGTH_SHORT);
    }


    /**
     * 显示长toast
     *
     * @param msg 信息
     */
    public static void showLong(String msg) {
        showToast(msg, Toast.LENGTH_LONG);
    }

    /**
     * 显示长toast
     *
     * @param strRes 字符串资源
     */
    public static void showLong(int strRes) {
        showToast(getContext().getString(strRes), Toast.LENGTH_LONG);
    }


    /**
     * 显示toast公共方法
     *
     * @param msg      字符串msg
     * @param duration 时长
     */
    private static void showToast(final String msg, final int duration) {

        if (sNoToast || sConfig == null || getContext() == null)
            return;

        if (CheckUtils.isEmpty(msg))
            return;

        if (sToast != null && !sConfig.oneToast) {
            sToast.cancel();
            sToast = null;
        }

        Runnable toastRunnable = new Runnable() {
            @Override
            public void run() {
                try {

                    if (sToast == null) {
                        sToast = new Toast(getContext());
                    }

                    // 位置
                    sToast.setDuration(duration);

                    if (sConfig.gravity != 0) {
                        sToast.setGravity(sConfig.gravity, sConfig.xOffset, sConfig.yOffset);
                    }

                    View toastLayout = sToast.getView();

                    if (toastLayout == null) {
                        toastLayout = LayoutInflater.from(getContext()).inflate(R.layout.view_toast, null, false);
                    }

                    sToast.setView(toastLayout);

                    initToastView(toastLayout, msg);

                    sToast.show();

                } catch (Exception e) {
                    LgUtils.e(e);
                }
            }
        };
        if (Looper.myLooper() == Looper.getMainLooper()) {
            toastRunnable.run();
        } else {
            sHandler.post(toastRunnable);
        }
    }


    private static void initToastView(View toastLayout, String msg) {

        TextView toastTv = toastLayout.findViewById(R.id.toast_text);
        ImageView toastIv = toastLayout.findViewById(R.id.toast_icon);

        // initView background
        if (sConfig.bgDrawable != null) {
            ViewUtils.setBackground(toastLayout, sConfig.bgDrawable);
        } else if (sConfig.bgRes != 0) {
            toastLayout.setBackgroundResource(sConfig.bgRes);
        } else {
            Drawable drawable = DrawableUtils.newRoundRectDrawable(sConfig.bgColor, sConfig.bgRadiusInDp);
            sConfig.setBgDrawable(drawable);
            ViewUtils.setBackground(toastLayout, drawable);
        }

        // initView text
        toastTv.setTextColor(sConfig.textColor);
        toastTv.setTextSize(sConfig.textSizeInSp);
        toastTv.setText(msg);

        // initView image
        if (sConfig.iconDrawable != null || sConfig.iconRes != 0) {
            ViewUtils.setVisibility(toastIv, View.VISIBLE);
            if (sConfig.iconDrawable != null)
                toastIv.setImageDrawable(sConfig.iconDrawable);
            else if (sConfig.iconRes != 0)
                toastIv.setImageResource(sConfig.iconRes);
        } else {
            ViewUtils.setVisibility(toastIv, View.GONE);
        }
    }


    /**
     * config
     */
    public static class Config {

        // 文字相关
        int textColor = Color.WHITE; // 文字颜色
        int textSizeInSp = 16; // 文字大小

        // toast 位置信息
        int gravity, xOffset, yOffset;

        // 公用一个 toast 或 cancel 掉原来的，重新构建一个新的
        boolean oneToast = true;

        // 背景颜色
        Drawable bgDrawable;
        int bgRes;
        int bgColor = Color.BLACK;
        int bgRadiusInDp = 5;

        // 图片资源
        int iconRes;
        Drawable iconDrawable;


        public Config setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Config setTextSizeInSp(int textSizeInSp) {
            this.textSizeInSp = textSizeInSp;
            return this;
        }

        public Config setGravity(int gravity, int xOffset, int yOffset) {
            this.gravity = gravity;
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            return this;
        }

        public Config setOneToast(boolean oneToast) {
            this.oneToast = oneToast;
            return this;
        }

        public Config setIconDrawable(Drawable iconDrawable) {
            this.iconDrawable = iconDrawable;
            return this;
        }

        public Config setIconRes(int iconRes) {
            this.iconRes = iconRes;
            return this;
        }


        public Config setBgDrawable(Drawable bgDrawable) {
            this.bgDrawable = bgDrawable;
            return this;
        }


        public Config setBgColor(int bgColor) {
            this.bgColor = bgColor;
            return this;

        }

        public Config setBgRadiusInDp(int bgRadiusInDp) {
            this.bgRadiusInDp = bgRadiusInDp;
            return this;
        }

        public void setBgRes(int bgRes) {
            this.bgRes = bgRes;
        }

    }
}
