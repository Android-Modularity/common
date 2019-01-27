package com.march.common.x;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.RequiresPermission;
import android.telephony.TelephonyManager;

import com.march.common.Common;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.ACCESS_WIFI_STATE;

/**
 * CreateAt : 2017/1/16
 * Describe : 检测网络环境
 *
 * @author chendong
 */
public class NetX {

    /**
     * 开启 wifi 设置页面
     */
    public static void openWirelessSettings() {
        Common.app().startActivity(
                new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        );
    }


    /**
     * 获取当前网络状态
     *
     * @return NetworkInfo
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    private static NetworkInfo getActiveNetworkInfo() {
        Context context = Common.app();
        if (context == null) {
            return null;
        }
        ConnectivityManager connectivityManager = getConnectivityManager(context.getApplicationContext());
        if (connectivityManager == null) {
            return null;
        }
        return connectivityManager.getActiveNetworkInfo();
    }

    /**
     * @return 网络是否连接，包括流量和 wifi
     */
    public static boolean isNetworkConnected() {
        NetworkInfo activeNetworkInfo = getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    /**
     * @return 网络是否可用，包括流量和 wifi
     */
    public static boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable();
    }


    /**
     * @return 当前连接网络的名字
     */
    public static String getNetworkOperatorName() {
        TelephonyManager tm = (TelephonyManager) Common.app().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getNetworkOperatorName() : "";
    }


    /**
     * wifi网络是否连接
     *
     * @return 是否连接
     */
    @RequiresPermission(ACCESS_WIFI_STATE)
    public static boolean isWifiConnected() {
        ConnectivityManager cm = getConnectivityManager(Common.app());
        if (cm != null) {
            NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI;
        }
        return false;
    }


    private static ConnectivityManager getConnectivityManager(Context app) {
        return (ConnectivityManager) app.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

}
