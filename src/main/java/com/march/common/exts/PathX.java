package com.march.common.exts;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * CreateAt : 2018/10/15
 * Describe :
 *
 * @author chendong
 */
public class PathX {

    /**
     * 文件 key
     */
    public static String fileKey;
    /**
     * app 父路径
     */
    public static String APP_ROOT_PATH;
    /**
     * 暂存路径
     */
    public static String TEMP_PATH;
    /**
     * 相册文件夹
     */
    public static String DCIM_PATH;
    /**
     * 不可见文件夹
     */
    public static String THUMB_PATH;
    /**
     * 下载文件夹
     */
    public static String DOWNLOAD_PATH;
    /**
     * 根路径
     */
    public static String BASE_PATH;

    public static void init(Context context) {
        init(context, null);
    }

    /**
     * 初始化路径
     *
     * @param context context
     * @param key     专门文件名
     */
    public static void init(Context context, String key) {
        if (key != null) fileKey = key;
        else fileKey = context.getPackageName();

        if (FileX.isSDCardValid()) {
            BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            BASE_PATH = context.getFilesDir().getAbsolutePath();
        }
        FileX.newDir(BASE_PATH);
        DCIM_PATH = new File(BASE_PATH, "/DCIM/Camera").getAbsolutePath();
        APP_ROOT_PATH = new File(BASE_PATH, fileKey).getAbsolutePath();
        TEMP_PATH = APP_ROOT_PATH + "/temp";
        THUMB_PATH = APP_ROOT_PATH + "/.thumb";
        DOWNLOAD_PATH = APP_ROOT_PATH + "/download";

        FileX.newDir(APP_ROOT_PATH);
        FileX.newDir(TEMP_PATH);
        FileX.newDir(THUMB_PATH);
        FileX.newDir(DOWNLOAD_PATH);
    }
}
